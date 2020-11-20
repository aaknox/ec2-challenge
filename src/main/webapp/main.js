//add event listener
document.getElementById('login-form').addEventListener('submit', signIn);

function signIn() {
    //get data from html form
    var username = document.getElementById('username-field').value;
    var password = document.getElementById('password-field').value;

    console.log('signIn function triggered!')
    console.log('Username: ' + username);
    console.log('Password: ' + password);
    //start some ajax stuff here
    var xhr = new XMLHttpRequest();
    xhr.onload = function() {
        if (this.status === 200) {
            //save session user
            console.log("Success")
            //to pass between pages
            localStorage.setItem('currentUser', username)
            //to pass to the server
            sessionStorage.setItem('currentUser', username)
            //give request the redirect URL
            window.location = "http://localhost:8080/SimpleMVCDemo/home.html"
            console.log("Local storage: " + localStorage.getItem('currentUser'))
             console.log("Session storage: " + sessionStorage.getItem('currentUser'))
            //stringify user info to send it with request to server
            JSON.stringify(this.responseText);
        }else if(this.status == 422){
        	
        }
    }
    xhr.open('POST', 'http://localhost:8080/SimpleMVCDemo/login', true);
    xhr.send();
}