<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bus Booking Portal</title>
<link rel="stylesheet" href="css/userportal.css?v=<%=System.currentTimeMillis()%>">

<script>
    function chooseSeat(el, seatNo) {
        if(el.classList.contains("booked")) return;

        document.getElementById("seatno").value = seatNo;

        // Remove previously selected seat
        document.querySelectorAll(".seat.selected").forEach(s => s.classList.remove("selected"));

        el.classList.add("selected");
    }
</script>
</head>
<body>

<%
	@SuppressWarnings("unchecked")
    List<String> busList = (List<String>) request.getAttribute("buslist");
	@SuppressWarnings("unchecked")
    List<Boolean> seatList = (List<Boolean>) request.getAttribute("seatList");
    String selectedBus = (String) request.getAttribute("bus");
    Double price = (Double) request.getAttribute("price");

%>

<nav class="navbar">
    <h2 class="logo">BusBooking</h2>
</nav>

<main class="container">

<!-- BUS SELECTION -->
<form id="busForm" action="viewseat" method="get" class="bus-select-form">
    <label for="bus">Select Bus:</label>
    <select name="bus" id="bus">
        <% for(String b : busList) { %>
            <option value="<%=b%>" <%= (b.equals(selectedBus) ? "selected" : "") %>><%=b%></option>
        <% } %>
    </select>
    <button type="submit" class="btn-submit">Show Seats</button>
</form>

<% if(seatList != null) { %>

<!-- BUS SEAT LAYOUT -->
<div class="bus-layout">

    <!-- LOWER DECK -->
    <div class="deck-box">
        <h3 class="deck-title">Lower Deck</h3>
        <div class="deck-row">
            <% for(int i = 0; i < seatList.size()/2; i += 4) { %>
                <div class="row">
                    <% for(int j = 0; j < 4; j++) { 
                        int seatNo = i + j + 1;
                        boolean available = seatList.get(seatNo - 1);
                    %>
                    <div class="seat-wrapper">
                        <% if(available) { %>
                            <div class="seat available" onclick="chooseSeat(this, <%=seatNo%>)">
                                <span class="seat-label"><%=seatNo%></span>
                            </div>
                            <div class="seat-price">₹<%= price %></div>
                        <% } else { %>
                            <div class="seat booked">
                                <span class="seat-label"><%=seatNo%></span>
                            </div>
                            <div class="seat-price sold">Booked</div>
                        <% } %>
                    </div>
                    <% } %>
                </div>
            <% } %>
        </div>
    </div>

    <!-- UPPER DECK -->
    <div class="deck-box">
        <h3 class="deck-title">Upper Deck</h3>
        <div class="deck-row">
            <% for(int i = seatList.size()/2; i < seatList.size(); i += 4) { %>
                <div class="row">
                    <% for(int j = 0; j < 4; j++) { 
                        int seatNo = i + j + 1;
                        boolean available = seatList.get(seatNo - 1);
                    %>
                    <div class="seat-wrapper">
                        <% if(available) { %>
                            <div class="seat available" onclick="chooseSeat(this, <%=seatNo%>)">
                                <span class="seat-label"><%=seatNo%></span>
                            </div>
                            <div class="seat-price">₹<%= price %></div>
                        <% } else { %>
                            <div class="seat booked">
                                <span class="seat-label"><%=seatNo%></span>
                            </div>
                            <div class="seat-price sold">Booked</div>
                        <% } %>
                    </div>
                    <% } %>
                </div>
            <% } %>
        </div>
    </div>

</div>

<!-- BOOKING FORM -->
<form action="userportal" method="post" class="booking-form">

    <input type="hidden" name="bus" value="<%= selectedBus %>">
    <input type="hidden" name="price" value="<%= price %>">

    <input type="text" name="name" class="input" placeholder="Passenger Name" required>
    <input type="tel" name="contact" class="input" placeholder="Contact Number" required>
    <input type="text" id="seatno" name="seatno" class="input" placeholder="Select a Seat" readonly required>

    <button type="submit" class="btn">Book Now</button>
</form>
<% } %>

</main>
</body>
</html>
