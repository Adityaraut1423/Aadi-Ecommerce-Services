document.addEventListener('DOMContentLoaded', () => {
    // 1. Check if the user is logged in
    const sessionUser = JSON.parse(localStorage.getItem('sessionUser'));
    
    // 2. Find the Login link in the navigation bar
    const navLinks = document.querySelectorAll('nav ul li a');
    let loginLink = null;
    
    navLinks.forEach(link => {
        // Look for the link that says "Login"
        if (link.textContent.trim() === 'Login') {
            loginLink = link;
        }
    });

    // 3. If a user is logged in and the link exists, update the UI
    if (sessionUser && loginLink) {
        // Change text to Logout and show their first name
        const firstName = sessionUser.name.split(' ')[0];
        loginLink.textContent = `Logout (${firstName})`;
        
        // Remove the href so it doesn't navigate to login.html
        loginLink.href = "#"; 
        
        // Add a click listener to handle the logout process
        loginLink.addEventListener('click', (e) => {
            e.preventDefault(); // Stop default link click behavior
            
            // Clear the session
            localStorage.removeItem('sessionUser');
            
            // Redirect back to home page
            window.location.href = 'index.html'; 
        });
    }
});