package com.gemini.admin.security;

import com.gemini.admin.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;


/**
 * Created with IntelliJ IDEA.
 * User: fran
 * Date: 2/23/18
 * Time: 3:20 PM
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.loadUserByUsername(username);
    }

    public static void main(String[] args) {
        try {
//            org.apache.commons.codec.binary.Base64.encodeBase64("Colónd5042356".getBytes());
            byte encoded[] = Base64.encode("Colond5042356".getBytes());
            System.out.println(new String(encoded));
            System.out.println(new String(Base64.decode(encoded)));
            System.out.println(new String(Base64.decode("Q29sw7NuZDUwNDIzNTY=".getBytes())));
            System.out.println(new String(Base64.decode("Q29s825kNTA0MjM1Ng==".getBytes("ISO-8859-1"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}