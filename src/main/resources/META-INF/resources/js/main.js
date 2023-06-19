
var originalFetch = window.fetch;
// Create a fetch interceptor
var fetchInterceptor = {
  fetch2: function(url, options0) {
    // Add the authorization header to the options object
    var token = localStorage.getItem("authorizationHeader"); // Replace with your actual token
    console.log("token", token);
    var options = options0 || {"headers":{}};
    options.headers = options.headers || {};
    options.headers["Authorization"] = token;
    alert(token);
    console.log("options", options);

    // Call the original fetch function with the modified options
    originalFetch(url, options);
  }
};

// Override the global fetch function with the interceptor

window.fetch = function(url, options) {
  fetchInterceptor.fetch2(url, options);
};

// Add an event listener to the 'DOMContentLoaded' event to ensure the page has finished loading
document.addEventListener("DOMContentLoaded", function() {
  // Find all <a> elements on the page
  var links = document.getElementsByTagName("a");

  // Iterate over the links and add the event listener to intercept the click event
//  for (var i = 0; i < links.length; i++) {
//    links[i].addEventListener("click", handleLinkClick);
//  }

  // Function to handle the link click event
  function handleLinkClick000(event) {
    event.preventDefault(); // Prevent the default link navigation behavior
    console.log(event);
    // Get the target URL from the clicked link
    var url = "/vets.html"//event.target.href;
    alert("event.target.href"+url);

        var xhr = new XMLHttpRequest();
        var token = localStorage.getItem("authorizationHeader"); // Replace with your actual token

        // Open the request with the modified URL and headers
        xhr.open("GET", url);
        xhr.setRequestHeader("Authorization", token);

        // Set the callback function for the request
        xhr.onload = function() {
          // Handle the response as needed
          // For example, you can open the page with the modified request headers
          window.open(url);
        };

        // Send the request
        xhr.send();
    // Fetch the URL using the modified global fetch function
//    window.fetch(url)
//      .then(function(response) {
//        // Handle the response as needed
//        // For example, you can redirect to the response URL
//            alert(response.url);
//
//        window.location.href = response.url;
//      })
//      .catch(function(error) {
//        // Handle errors
//        console.error("Error occurred during the request.", error);
//      });
  }
});