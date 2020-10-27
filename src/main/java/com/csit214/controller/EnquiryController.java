package com.csit214.controller;

import com.csit214.models.Enquiry;
import com.csit214.models.FrequentFlyerAccount;
import com.csit214.payload.ApiResponse;
import com.csit214.payload.EnquiryPayload;
import com.csit214.repository.EnquiryRepository;
import com.csit214.repository.UserRepository;
import com.csit214.security.CurrentUser;
import com.csit214.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/enquiry")
public class EnquiryController {
    @Autowired
    private EnquiryRepository enquiryRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/userEnquiry")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> makeEnquiry(@RequestBody EnquiryPayload request, @CurrentUser UserPrincipal userPrincipal) {
        //  public Enquiry(FrequentFlyerAccount from, String subject, String content, @Nullable String reply, boolean status) {
        FrequentFlyerAccount account = userRepository.findById(userPrincipal.getId()).orElse(null);
        Enquiry enquiry = new Enquiry(account, request.getSubject(), request.getContent(), "", false);
        Set<Enquiry> enquiries = account.getEnquiries();
        enquiries.add(enquiry);
        account.setEnquiries(enquiries);
        if (enquiry.getFrom() != null) {
            enquiryRepository.save(enquiry);
            userRepository.save(account);
            return new ResponseEntity(new ApiResponse(true, "Enquiry created"),
                    HttpStatus.valueOf(200));
        } else {
            return new ResponseEntity(new ApiResponse(false, "enquiry discarded"),
                    HttpStatus.valueOf(200));
        }
    }

    @DeleteMapping("/userEnquiry/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteEnquiry(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal) {
        enquiryRepository.deleteById(id);
        return new ResponseEntity(new ApiResponse(true, "Enquiry deleted"),
                HttpStatus.valueOf(200));
    }


    @GetMapping("/admin/allEnquiries")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Enquiry> getALlEnquiries() {
        return enquiryRepository.findAll();
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public Set<Enquiry> getALlEnquiriesUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId()).orElse(null).getEnquiries();
    }

    @GetMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Enquiry getEnquriy(@PathVariable Long id) {
        return enquiryRepository.findById(id).orElse(null);
    }



    @PutMapping("/admin/allEnquiries/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> reply(@PathVariable Long id, @RequestBody EnquiryPayload enquiryPayload ) {
        Enquiry enquiry = enquiryRepository.findById(id).orElse(null);
        enquiry.setReply(enquiryPayload.getReply());
        enquiry.setStatus(true);
        enquiryRepository.save(enquiry);

        return new ResponseEntity(new ApiResponse(true, "Enquiry updated"),
                HttpStatus.valueOf(200));
    }

}
