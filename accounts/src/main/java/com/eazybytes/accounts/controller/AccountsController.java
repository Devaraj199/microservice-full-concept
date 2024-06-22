package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.constants.AccountConstants;
import com.eazybytes.accounts.dto.AccountContactInfoDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ResponseDto;
import com.eazybytes.accounts.service.impl.AccountsServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api")
public class AccountsController {

  AccountsServiceImpl accountsService;
  @Autowired
    public AccountsController(AccountsServiceImpl accountsService){
        this.accountsService = accountsService;
    }

  @Autowired
  Environment environment;

  @Value("${build.version}")
  private  String buildVersion;
    @Autowired
    AccountContactInfoDto accountContactInfoDto;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
        accountsService.createAccount(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDto(AccountConstants.STATUS_201,AccountConstants.MESSAGE_201)
        );
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam String mobileNumber) {
      CustomerDto customerDto =   accountsService.fetchAccount(mobileNumber);
      return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccount(@Valid @RequestBody CustomerDto customerDto){
      Boolean isUpdated =   accountsService.updateAccount(customerDto);
      if(isUpdated){
          return ResponseEntity.status(HttpStatus.OK)
                  .body(new ResponseDto(AccountConstants.STATUS_200,AccountConstants.MESSAGE_200));
      }
      else {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                  .body(new ResponseDto(AccountConstants.STATUS_417,AccountConstants.MESSAGE_417_UPDATE      ));
      }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam String mobileNumber){
      Boolean isDeleted=  accountsService.deleteAccount(mobileNumber);
      if(isDeleted){
          return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountConstants.STATUS_200,AccountConstants.MESSAGE_200));
      }
      else {
          return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(AccountConstants.STATUS_417,AccountConstants.MESSAGE_417_DELETE));
      }

    }

    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        System.out.println("java version getting invoked");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }

    @GetMapping("/contact-info")
    public ResponseEntity<AccountContactInfoDto> getContactInfo(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(accountContactInfoDto);
    }
}
