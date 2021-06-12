<%-- 
    Document   : index
    Created on : 25/05/2021, 3:36:02 PM
    Author     : Shelby Mun 19049176 ; Angelo Ryndon 18028033
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Movie RESTful Service</title>
    </head>
    <body>
        <h1>Movie RESTful page made by Shelby & Angelo!</h1>
        <p/>
            <a href="<%= response.encodeURL(request.getContextPath())%>/movieworldservice/movies">Get all movies</a>
            <p/>
            <a href="<%= response.encodeURL(request.getContextPath())%>/movieworldservice/details/Godzilla VS Kong">Get Godzilla VS Kong movie details</a>
            <p />
            <a href="<%= response.encodeURL(request.getContextPath())%>/movieworldservice/movies/ticket">View booked movie tickets here</a>
            <p />
            <form action="<%= response.encodeURL(request.getContextPath())%>/movieworldservice/movies/ticket" method="POST">
                <p>
                    Movie to book a ticket:
                    <input type="text" name="Ticket"/>
                </p>
                <input type="submit" value="Book ticket"/>
            </form>
    </body>
</html>
