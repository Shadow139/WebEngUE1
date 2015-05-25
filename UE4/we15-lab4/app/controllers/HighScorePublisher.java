package controllers;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceException;

import twitter.TwitterClient;
import twitter.TwitterStatusMessage;
import at.ac.tuwien.big.we.highscore.Failure;
import at.ac.tuwien.big.we.highscore.PublishHighScoreEndpoint;
import at.ac.tuwien.big.we.highscore.PublishHighScoreService;
import at.ac.tuwien.big.we.highscore.data.GenderType;
import at.ac.tuwien.big.we.highscore.data.HighScoreRequestType;
import at.ac.tuwien.big.we.highscore.data.UserDataType;
import at.ac.tuwien.big.we.highscore.data.UserType;
import models.JeopardyUser;
import models.JeopardyUser.Gender;
import models.Player;

public class HighScorePublisher implements Runnable{
	UserType winner;
	UserType loser;

	public HighScorePublisher(Player winner, Player loser) {
		super();

		this.winner = convertToUserType(winner);
		this.loser = convertToUserType(loser);
	}
	
	private UserType convertToUserType(Player player){
		UserType userType = new UserType();
		/*
		try{
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(player.getUser().getBirthDate());
			XMLGregorianCalendar date2 = null;
			try {
				date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			} catch (DatatypeConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			userType.setBirthDate(date2);
		}catch(NullPointerException n){
			//wupsydaisy
		}
		userType.setFirstName(player.getUser().getFirstName());
		userType.setGender(GenderType.fromValue(player.getUser().getGender().name()));
		userType.setLastName(player.getUser().getLastName());
		userType.setPoints(player.getProfit());
		*/
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date(0));
		XMLGregorianCalendar date2 = null;
		try {
			date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userType.setBirthDate(date2);
		userType.setFirstName("John");
		userType.setGender(GenderType.FEMALE);
		userType.setLastName("John");
		userType.setPassword("John");
		userType.setPoints(0);
		
		return userType;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {

			PublishHighScoreService service = new PublishHighScoreService();
			PublishHighScoreEndpoint endpoint = service
					.getPort(PublishHighScoreEndpoint.class);
			UserDataType j = new UserDataType();
			
			j.setWinner(winner);
			j.setLoser(loser);
			
			HighScoreRequestType request = new HighScoreRequestType();
			request.setUserKey("3ke93-gue34-dkeu9");
			request.setUserData(j);
			
			String uuid = endpoint.publishHighScore(request);
			//TwitterClient.share(new TwitterStatusMessage(winnerName, uuid,new Date()));

		} catch (Failure e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WebServiceException e) {
			e.printStackTrace();
		}
	}
}
