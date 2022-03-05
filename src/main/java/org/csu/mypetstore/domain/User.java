package org.csu.mypetstore.domain;

import lombok.Data;

@Data
public class User {
    private String id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String languagepre;
    private String favoritecata;
    private String iflist;
    private String ifbanner;

}
