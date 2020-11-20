function loadUser() {
	var username = window.localStorage.getItem('currentUser');
	console.log("Session student: " + username)
	document.getElementById('form-title').innerHTML = "Welcome, " + username
			+ '!';
}

function signOut() {
	console.log('signOut function triggered!')
	// clear session user
	localStorage.clear();
	sessionStorage.clear();
	//server is handling the redirect
}

function profile() {
	console.log('profile function triggered!')
	//server gives us a json object
	//xhr transforms this object into html
	//show the results
}