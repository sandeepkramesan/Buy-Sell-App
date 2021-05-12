
## Buying And Selling App  

The App allows registered members to post/sell & to buy products.  

The App establishes security through Authentication and Authorisation using >JWT.  

---

## Steps to Follow

1. Register member 
	* `user/` using browser
	* `user/register` using Postman App
2. Once the Member is Created, Go to Postman App.
3. Go to url localhost:user-service-port/login and give your username and password in JSON.
4. You'll get the Authentication Token if given correct credentials else 403 Forbidden Error.
5. When you get the Token, use that Token in the Header Authorisation under Bearer Token Option.
6. You can only get into links that you are permitted.
	* for info on link permissions , go to Security Configuration Class in ` finalproject / user-service / src / main / java / com / project / config /`.  

---

## Tools Used

* Spring Tool Suite 4
* Postman