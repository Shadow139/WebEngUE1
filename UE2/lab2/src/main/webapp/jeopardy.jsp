<?xml version="1.0" encoding="UTF-8"?>

	<%@page import="at.ac.tuwien.big.we15.lab2.api.Category"%>
	<%@page import="at.ac.tuwien.big.we15.lab2.api.Question"%>
	<%@page import="at.ac.tuwien.big.we15.lab2.api.Game"%>
	<%@page import="at.ac.tuwien.big.we15.lab2.api.Player"%>
	<%@page import="java.util.List"%>
   
	<%
	List<Category> categoryList = (List) session.getAttribute("categoryList");
	Game game = (Game) session.getAttribute("game");
	Integer player1info = (Integer) session.getAttribute("player1info");
	Integer player2info = (Integer) session.getAttribute("player2info");
	String player2Choice = (String) session.getAttribute("player2Choice");
	%>
   

   <!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Business Informatics Group Jeopardy! - Fragenauswahl</title>
        <link rel="stylesheet" type="text/css" href="style/base.css" />
        <link rel="stylesheet" type="text/css" href="style/screen.css" />
        <script src="js/jquery.js" type="text/javascript"></script>
        <script src="js/framework.js" type="text/javascript"></script>
   </head>
   <body id="selection-page">
      <a class="accessibility" href="#question-selection">Zur Fragenauswahl springen</a>
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
				<li><a class="orangelink navigationlink" id="logoutlink" title="Klicke hier um dich abzumelden" href="#" accesskey="l">Abmelden</a></li>
			</ul>
		</nav>

      <!-- Content -->
      <div role="main">
         <!-- info -->
         <section id="gameinfo" aria-labelledby="gameinfoinfoheading">
            <h2 id="gameinfoinfoheading" class="accessibility">Spielinformationen</h2>
            <section id="firstplayer" class="playerinfo leader" aria-labelledby="firstplayerheading">
               <h3 id="firstplayerheading" class="accessibility">Führender Spieler</h3>
               <img class="avatar" src="img/avatar/<%= game.getPlayer1().getAvatar().getImageFull() %>" alt="Spieler-Avatar Black Widow" />
               <table>
                  <tr>
                     <th class="accessibility">Spielername</th>
                     <td class="playername"><%= game.getPlayer1().getName() %> (Du)</td>
                  </tr>
                  <tr>
                     <th class="accessibility">Spielerpunkte</th>
                     <td class="playerpoints"><%= game.getPlayer1().getWinnings() %> &#8364;</td>
                  </tr>
               </table>
            </section>
            <section id="secondplayer" class="playerinfo" aria-labelledby="secondplayerheading">
               <h3 id="secondplayerheading" class="accessibility">Zweiter Spieler</h3>
               <img class="avatar" src="img/avatar/<%= game.getPlayer2().getAvatar().getImageFull() %>" alt="Spieler-Avatar Deadpool" />
               <table>
                  <tr>
                     <th class="accessibility">Spielername</th>
                     <td class="playername"><%= game.getPlayer2().getName() %></td>
                  </tr>
                  <tr>
                     <th class="accessibility">Spielerpunkte</th>
                     <td class="playerpoints"><%= game.getPlayer2().getWinnings() %> &#8364;</td>
                  </tr>
               </table>
            </section>
            <p id="round">Fragen: <%= game.getQuestionsAsked() %> / 10</p>
         </section>

         <!-- Question -->
         <section id="question-selection" aria-labelledby="questionheading">
            <h2 id="questionheading" class="black accessibility">Jeopardy</h2>
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
            	if(player2Choice != null){
            		%>
            			<p class="user-info"><%= player2Choice%> </p>
            		<%
            	}
            }
            %>
            <form id="questionform" action="BigJeopardyServlet" method="post">

               <fieldset>
               <legend class="accessibility">Fragenauswahl</legend>
               <section class="questioncategory" aria-labelledby="tvheading">
                  <h3 id="tvheading" class="tile category-title"><span class="accessibility">Kategorie: </span><%= categoryList.get(0).getName()%></h3>
                  <ol class="category_questions">
                   
                 <% for (int i = 0; i < categoryList.get(0).getQuestions().size(); i++) { %>
                  
                     <li><input name="question_selection" id="question_<%= i+1 %>" value="<%= i+1 %>" type="radio" <%if(!categoryList.get(0).getQuestions().get(i).isActive()){%> disabled = "disabled" <% }  %>/><label class="tile clickable" for="question_<%= i+1 %>"><%= categoryList.get(0).getQuestions().get(i).getValue() %> &#8364;</label></li>
                    
                  <% } %>   
                  <% int previousSize = categoryList.get(0).getQuestions().size(); %>
                  
                  </ol>
               </section>
               <section class="questioncategory" aria-labelledby="ssdheading">
                  <h3 id="ssdheading" class="tile category-title"><span class="accessibility">Kategorie: </span><%= categoryList.get(1).getName()%></h3>
                  <ol class="category_questions">
                  
                 <% for (int i = 0; i < categoryList.get(1).getQuestions().size(); i++) { %>
                  
                     <li><input name="question_selection" id="question_<%= i+1+previousSize %>" value="<%= i+1+previousSize %>" type="radio" <%if(!categoryList.get(1).getQuestions().get(i).isActive()){%> disabled = "disabled" <% }  %>/><label class="tile clickable" for="question_<%= i+1+previousSize %>"><%= categoryList.get(1).getQuestions().get(i).getValue()%> &#8364;</label></li>
                    
                  <% } %> 
                  <% previousSize += categoryList.get(1).getQuestions().size(); %>
                 
                  </ol>
               </section>
               <section class="questioncategory" aria-labelledby="webheading">
                  <h3 id="webheading" class="tile category-title"><span class="accessibility">Kategorie: </span><%= categoryList.get(2).getName()%></h3>
                  <ol class="category_questions">
                  
                 <% for (int i = 0; i < categoryList.get(2).getQuestions().size(); i++) { %>
                  
                     <li><input name="question_selection" id="question_<%= categoryList.get(2).getQuestions().get(i).getId() %>" value="<%= categoryList.get(2).getQuestions().get(i).getId() %>" type="radio" <%if(!categoryList.get(2).getQuestions().get(i).isActive()){%> disabled = "disabled" <% }  %>/><label class="tile clickable" for="question_<%= categoryList.get(2).getQuestions().get(i).getId() %>"><%= categoryList.get(2).getQuestions().get(i).getValue() %> &#8364;</label></li>
                    
                  <% } %>   
                  <% previousSize += categoryList.get(2).getQuestions().size(); %>
                  
                  </ol>
               </section>
               <section class="questioncategory" aria-labelledby="sportheading">
                  <h3 id="sportheading" class="tile category-title"><span class="accessibility">Kategorie: </span><%= categoryList.get(3).getName()%></h3>
                  <ol class="category_questions">
                  
                 <% for (int i = 0; i < categoryList.get(3).getQuestions().size(); i++) { %>
                  
                     <li><input name="question_selection" id="question_<%= categoryList.get(3).getQuestions().get(i).getId() %>" value="<%= categoryList.get(3).getQuestions().get(i).getId() %>" type="radio" <%if(!categoryList.get(3).getQuestions().get(i).isActive()){%> disabled = "disabled" <% }  %>/><label class="tile clickable" for="question_<%= categoryList.get(3).getQuestions().get(i).getId() %>"><%= categoryList.get(3).getQuestions().get(i).getValue() %> &#8364;</label></li>
                    
                  <% } %>   
                  <% previousSize += categoryList.get(3).getQuestions().size(); %>
                  
                  </ol>
               </section>
               <section class="questioncategory" aria-labelledby="tuwienheading">
                  <h3 id="tuwienheading" class="tile category-title"><span class="accessibility">Kategorie: </span><%= categoryList.get(4).getName()%></h3>
                  <ol class="category_questions">
                  
                 <% for (int i = 0; i < categoryList.get(4).getQuestions().size(); i++) { %>
                  	<%if(!categoryList.get(4).getQuestions().get(i).isActive()){
                  		System.out.println(categoryList.get(4).getQuestions().get(i).getText());
                  	}%>
                     <li><input name="question_selection" id="question_<%= categoryList.get(4).getQuestions().get(i).getId() %>" value="<%= categoryList.get(4).getQuestions().get(i).getId() %>" type="radio" <%if(!categoryList.get(4).getQuestions().get(i).isActive()){System.out.println("this question is disabled: " + categoryList.get(4).getQuestions().get(i).getText());%> disabled = "disabled" <% }  %>/><label class="tile clickable" for="question_<%= categoryList.get(4).getQuestions().get(i).getId() %>"><%= categoryList.get(4).getQuestions().get(i).getValue() %> &#8364;</label></li>
                    
                  <% } %>   
                  
                  </ol>
               </section>
               </fieldset>
               <input class="greenlink formlink clickable" name="submit" id="next" type="submit" value="waehlen" accesskey="s" disabled="disabled" />
            </form>
         </section>

         <section id="lastgame" aria-labelledby="lastgameheading">
            <h2 id="lastgameheading" class="accessibility">Letztes Spielinfo</h2>
            <p>Letztes Spiel: Nie</p>
         </section>
		</div>

      <!-- footer -->
      <footer role="contentinfo">&#169; 2015 BIG Jeopardy!</footer>

	  <script type="text/javascript">
            //<![CDATA[

            // initialize time
            $(document).ready(function() {
                // set last game
                if(supportsLocalStorage()) {
	                var lastGameMillis = parseInt(localStorage['lastGame'])
	                if(!isNaN(parseInt(localStorage['lastGame']))){
	                    var lastGame = new Date(lastGameMillis);
	                	$("#lastgame p").replaceWith('<p>Letztes Spiel: <time datetime="'
	                			+ lastGame.toUTCString()
	                			+ '">'
	                			+ lastGame.toLocaleString()
	                			+ '</time></p>')
	                }
            	}
            });
            
            $(":radio").bind('click', function() {
            	  $("#next").prop('disabled', false);
            	});
            //]]>
        </script>
    </body>
</html>
