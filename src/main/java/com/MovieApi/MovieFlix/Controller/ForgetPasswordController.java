package com.MovieApi.MovieFlix.Controller;

import java.time.Instant;
import java.util.Date;
import java.util.Random;

import org.apache.logging.log4j.CloseableThreadContext.Instance;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.MovieApi.MovieFlix.Security.Entity.ChangePassword;
import com.MovieApi.MovieFlix.Security.Entity.ForgetPassword;
import com.MovieApi.MovieFlix.Security.Entity.MailBody;
import com.MovieApi.MovieFlix.Security.Entity.User;
import com.MovieApi.MovieFlix.Security.Repos.ForgetPasswordRepo;
import com.MovieApi.MovieFlix.Security.Repos.UserRepo;
import com.MovieApi.MovieFlix.Service.EmailService;

@RestController
@RequestMapping("/forget")
public class ForgetPasswordController {

		private ForgetPasswordRepo forgetRepo;
		private UserRepo userRepo;
		private EmailService emailServ;
		private PasswordEncoder encoder;
		public ForgetPasswordController(ForgetPasswordRepo forgetRepo, UserRepo userRepo, EmailService emailServ,
				PasswordEncoder encoder) {
			super();
			this.forgetRepo = forgetRepo;
			this.userRepo = userRepo;
			this.emailServ = emailServ;
			this.encoder = encoder;
		}
		
		
		@PostMapping("/verifyEmail/{email}")
		public ResponseEntity<?> verifyEmail(@PathVariable String email){
			
				User user = userRepo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("user with email not found"));
				
				int otp = otpGenerator();
				
				MailBody mail = MailBody.builder().to(user.getEmail()).text("OTP is "+otp)
								.subject("Forget Password").build();
				
				ForgetPassword forgetPassword = ForgetPassword.builder().otp(otp).user(user).ExpirationTime(new Date(System.currentTimeMillis()+20*10000)).build();
				
				emailServ.SendMail(mail);
				
				forgetRepo.save(forgetPassword);
				
			return ResponseEntity.ok("EmailSend sucessfully");
		}


		private Integer otpGenerator() {
			Random random = new Random();
			return random.nextInt(1000, 99999);
					
		}
		
		@PostMapping("/verifyOTP/{email}/{Otp}")
		public ResponseEntity<?> verifyOtp(@PathVariable String email,@PathVariable Integer Otp){
				
					User user = userRepo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("Email is not correct"));
					
					ForgetPassword forgetPass = forgetRepo.findByUserAndOtp(user, Otp).orElseThrow(()->new UsernameNotFoundException("Otp is not correct"));
					
					if(forgetPass.getExpirationTime().before(Date.from(Instant.now()))){
						forgetRepo.deleteById(forgetPass.getFid());
						return ResponseEntity.badRequest().body("Otp is expired now!!");	
						
					}
					
					return ResponseEntity.ok().body("OTP Verified");	
					
		}
		
		@PostMapping("/changePass/{email}")
		public ResponseEntity<?> changePass(@RequestBody ChangePassword password,@PathVariable String email){
				
					User user = userRepo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("Email is not correct"));
					
					
					if(!password.oldPass().equals(password.newPass())){
						return ResponseEntity.badRequest().body("password not match");						
					}
					
						String encode = encoder.encode(password.newPass());
						user.setPassword(encode);
						userRepo.updatePassword(email, encode);
					return ResponseEntity.ok().body("OTP Verified");	
					
		}
}












