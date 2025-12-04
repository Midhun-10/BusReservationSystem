<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.entities.Bus, com.entities.Seat" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Inspect Bus</title>
<link rel="stylesheet" href="css/inspectbus.css">
</head>
<body>

<h1 class="title">Inspect Bus</h1>

<!-- ================= BUS SELECTION ================= -->
<div class="card">
    <form action="busdetail" method="post">
        <label for="busSelect">Select Bus:</label>
        <select name="bus_name" id="busSelect" required>
            <option value="">-- Select a Bus --</option>
            <%	@SuppressWarnings("unchecked")
                List<Bus> list = (List<Bus>) request.getAttribute("buslist");
                if (list != null) {
                    for (Bus b : list) {
            %>
                <option value="<%= b.getName() %>" <%= (request.getParameter("bus_name") != null && request.getParameter("bus_name").equals(b.getName())) ? "selected" : "" %>>
                    <%= b.getName() %>
                </option>
            <%      
                    }
                }
            %>
        </select>

        <button type="submit">View Details</button>
        <button type="button" onclick="window.location='adminportal.html'">Go Back</button>
    </form>
</div>

<hr>

<!-- ============ BUS DETAILS + SEAT GRID ============= -->
<%
    Bus bus = (Bus) request.getAttribute("bus");
	@SuppressWarnings("unchecked")
    List<Seat> seats = (List<Seat>) request.getAttribute("seats");

    if (bus != null && seats != null) {
%>

<div class="card">

    <h2>Bus Details</h2>
    <p><strong>Vehicle No:</strong> <%= bus.getVehNumber() %></p>
    <p><strong>Name:</strong> <%= bus.getName() %></p>
    <p><strong>Destination:</strong> <%= bus.getDestination() %></p>
    <p><strong>Total Seats:</strong> <%= bus.getTotalSeats() %></p>
    <p><strong>Price:</strong> ₹<%= bus.getPrice() %></p>
    <p><strong>Contact:</strong> <%= bus.getContact() %></p>

    <h2>Seat Availability</h2>
    <div class="seat-grid">
        <% for (Seat s : seats) { %>
            <div class="seat <%= s.isAvailable() ? "available" : "booked" %>">
                <%= s.getSeatNumber() %>
            </div>
        <% } %>
    </div>
</div>

<% } %>

</body>
</html>
