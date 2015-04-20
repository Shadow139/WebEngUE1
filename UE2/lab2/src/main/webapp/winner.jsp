<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>
	<%@page import="at.ac.tuwien.big.we15.lab2.api.Category"%>
	<%@page import="at.ac.tuwien.big.we15.lab2.api.Question"%>
	<%@page import="at.ac.tuwien.big.we15.lab2.api.Game"%>
	<%@page import="at.ac.tuwien.big.we15.lab2.api.Answer"%>
	<%@page import="at.ac.tuwien.big.we15.lab2.api.Player"%>
	<%@page import="java.util.List"%>
	
	<%
	Game game = (Game) session.getAttribute("game");
    Integer player1info = (Integer) session.getAttribute("player1info");
	Integer player2info = (Integer) session.getAttribute("player2info");
	%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Business Informatics Group Jeopardy! - Gewinnanzeige</title>
        <link rel="stylesheet" type="text/css" href="style/base.css" />
        <link rel="stylesheet" type="text/css" href="style/screen.css" />
		  <script src="js/jquery.js" type="text/javascript"></script>
        <script src="js/framework.js" type="text/javascript"></script>
    </head>
    <body id="winner-page">
      <a class="accessibility" href="#winner">Zur Gewinnanzeige springen</a>
      <!-- Header -->
      <header role="banner" aria-labelledby="bannerheading">
         <h1 id="bannerheading">
            <span class="accessibility">Business Informatics Group </span><span class="gametitle">Jeopardy!</span>
         </h1>
      </header>

      <!-- Navigation -->
		<nav role="navigation" aria-labelledby="navheading">
			<h2 id="navheading" class="accessibility">Navigation</h2>
			<ul>
				<li><a class="orangelink navigationlink" id="logoutlink" title="Klicke hier um dich abzumelden" href="login.xhtml" accesskey="l">Abmelden</a></li>
			</ul>
		</nav>

      <!-- Content -->
      <div role="main">
         <section id="gameinfo" aria-labelledby="winnerinfoheading">
            <h2 id="winnerinfoheading" class="accessibility">Gewinnerinformationen</h2>
            <%if(player1info != null){
            	if(player1info > 0){
            		%>
            			<p class="user-info positive-change">Du hast richtig geantwortet: +<%= player1info %> &#8364;</p>
            		<%
            	}else{
            		%>
	        			<p class="user-info negative-change">Du hast falsch geantwortet: <%= player1info %> &#8364;</p>
	        		<%
            	}
            	if(player2info > 0){
            		%>
            			<p class="user-info positive-change"><%= game.getPlayer2().getName() %> hat richtig geantwortet: +<%= player2info %> &#8364;</p>
            		<%
            	}else{
            		%>
	        			<p class="user-info negative-change"><%= game.getPlayer2().getName() %> hat falsch geantwortet: <%= player2info %> &#8364;</p>
	        		<%
            	}
            }
            %>
            <section class="playerinfo leader" aria-labelledby="winnerannouncement">
               <h3 id="winnerannouncement">Gewinner: <%= game.getWinner().getName() %></h3>
               <img class="avatar" src="img/avatar/<%= game.getWinner().getAvatar().getImageFull() %>" alt="Spieler-Avatar <%= game.getWinner().getAvatar().getName() %>" />
               <table>
                  <tr>
                     <th class="accessibility">Spielername</th>
                     <td class="playername"> <%= game.getWinner().getName() %></td>
                  </tr>
                  <tr>
                     <th class="accessibility">Spielerpunkte</th>
                     <td class="playerpoints">&#8364;  <%= game.getWinner().getWinnings() %></td>
                  </tr>
               </table>
            </section>
            <section class="playerinfo" aria-labelledby="loserheading">
               <h3 id="loserheading" class="accessibility">Verlierer: <%= game.getLooser().getName() %></h3>
               <img class="avatar" src="img/avatar/<%= game.getLooser().getAvatar().getImageFull() %>" alt="Spieler-Avatar <%= game.getWinner().getAvatar().getName() %>" />
               <table>
                  <tr>
                     <th class="accessibility">Spielername</th>
                     <td class="playername"> <%= game.getLooser().getName() %></td>
                  </tr>
                  <tr>
                     <th class="accessibility">Spielerpunkte</th>
                     <td class="playerpoints">&#8364;  <%= game.getLooser().getWinnings() %></td>
                  </tr>
               </table>
            </section>
         </section>
         <section id="newgame" aria-labelledby="newgameheading">
             <h2 id="newgameheading" class="accessibility">Neues Spiel</h2>
         	<form action="BigJeopardyServlet" method="post">
               	<input class="clickable orangelink contentlink" id="submit" type="submit" name="submit" value="Neues Spiel" />
            </form>
         </section>
      </div>
        <!-- footer -->
        <footer role="contentinfo">&#169; 2015 BIG Jeopardy</footer>
		<script type="text/javascript">
        //<![CDATA[
           $(document).ready(function(){
         	   if(supportsLocalStorage()){
         		   localStorage["lastGame"] = new Date().getTime();
         	   }
           });
        //]]>
        </script>
    </body>
</html>
