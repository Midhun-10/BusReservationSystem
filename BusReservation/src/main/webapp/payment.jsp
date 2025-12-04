<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String amount = request.getAttribute("amount") != null ? request.getAttribute("amount").toString() : "100";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Payment Portal</title>
    <link rel="stylesheet" href="./css/payment.css">
</head>
<body>
    <div class="payment-container">
        <h2>Pay via GPay</h2>
        <p>Amount to pay: <b><%= amount %></b></p>

        <!-- QR Code (static image) -->
        <div class="qr-code">
            <img src="./images/qr-code.png" alt="GPay QR Code" />
        </div>

        <div class="timer">
            <p>Complete payment within:</p>
            <h3 id="countdown">05:00</h3>
        </div>

        <p class="info">Scan the QR code using your GPay app to pay the above amount.</p>

        <button onclick="paymentSuccessful()">I Have Paid</button>
    </div>

    <script>
        let timer = 300; // seconds
        const countdownElement = document.getElementById("countdown");
        let interval = setInterval(updateTimer, 1000);

        function updateTimer() {
            let minutes = Math.floor(timer / 60);
            let seconds = timer % 60;
            seconds = seconds < 10 ? '0' + seconds : seconds;
            countdownElement.textContent = minutes + ":" + seconds;

            if (timer === 0) {
                clearInterval(interval);
                alert("Transaction time expired!");
                countdownElement.textContent = "00:00";
            }
            timer--;
        }

        function paymentSuccessful() {
            clearInterval(interval); // stop the timer
            alert("Payment successful! Redirecting...");
            window.location.href = "booking-success.html"; // redirect
        }
    </script>
</body>
</html>
