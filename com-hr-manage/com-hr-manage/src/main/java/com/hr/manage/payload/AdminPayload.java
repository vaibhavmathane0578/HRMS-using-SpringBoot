package com.hr.manage.payload;

public class AdminPayload {

	private String id ;	
	private String firstName ;
		private String lastName;
		private String contactNumber;
		private String email;
		private String password;
		private String createdAt;
		private String updatedAt;
		public AdminPayload() {
			super();
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getContactNumber() {
			return contactNumber;
		}
		public void setContactNumber(String contactNumber) {
			this.contactNumber = contactNumber;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(String createdAt) {
			this.createdAt = createdAt;
		}
		public String getUpdatedAt() {
			return updatedAt;
		}
		public void setUpdatedAt(String updatedAt) {
			this.updatedAt = updatedAt;
		}
		@Override
		public String toString() {
			return "AdminPayload [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", contactNumber="
					+ contactNumber + ", email=" + email + ", password=" + password + ", createdAt=" + createdAt
					+ ", updatedAt=" + updatedAt + "]";
		}

}