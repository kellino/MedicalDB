package org.ucl.gui;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginHandler {
	private final String root = "root";
	private final String passwordHex = "7cbdd4e997c3b8e759f8d579bb30f6f1";
	private String username;
	private String password;

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	boolean checkLoginDetails() {
		if (username.equals(root) && checkMD5(password).equals(passwordHex)) {
			return true;
		}
		return false;
	}

	/**
	 * generates an MD5 checksum from the password input string, which is used
	 * to compare against the stored encrypted password string
	 * 
	 * @param String
	 *            inputPassword
	 * @return String hexString;
	 */
	private String checkMD5(String inputPassword) {
		MessageDigest digester = null;
		try {
			digester = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException nae) {
			nae.printStackTrace();
		}

		digester.update(inputPassword.getBytes());
		byte[] hash = digester.digest();
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			if ((0xff & hash[i]) < 0x10) {
				hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
			} else {
				hexString.append(Integer.toHexString(0xFF & hash[i]));
			}
		}
		return hexString.toString();
	}
}
