package io.javabrains.ipldashboard.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.javabrains.ipldashboard.model.Match;
import io.javabrains.ipldashboard.model.Team;
import io.javabrains.ipldashboard.repository.MatchRepository;
import io.javabrains.ipldashboard.repository.TeamRepository;

@RestController
@CrossOrigin
public class TeamController {

	 private TeamRepository teamRepository;
	 private MatchRepository matchRepository;
	    
	 public TeamController(TeamRepository teamRepository, MatchRepository matchRepository) {
	        this.teamRepository = teamRepository;
	        this.matchRepository = matchRepository;
	    }
	
//	@GetMapping("/team/{teamName}")
//    public Team getTeam(@PathVariable String teamName) {
//		Team team = new Team();
//       team =  this.teamRepository.findByTeamName(teamName) ;
//      team.setMatches(this.matchRepository.findLatestMatchesByTeam(teamName,4));
//      return team;
//    }
	 
	 @GetMapping("/team")
		 public Iterable<Team> getAllTeam(){
			 return this.teamRepository.findAll();
		 }
	 
	 
	 @GetMapping("/team/{teamName}")
	 public Team getTeam(@PathVariable String teamName) {
	     Team team = this.teamRepository.findByTeamName(teamName);
	     if (team != null) {
	         List<Match> latestMatches = this.matchRepository.findLatestMatchesByTeam(teamName, 4);
	         if (latestMatches != null) {
	             team.setMatches(latestMatches);
	         } else {
	             // Handle the case when latestMatches is null
	             // You can choose to set an empty list or handle the error as appropriate
	         }
	     }
	     return team;
	 }
	 
	 @GetMapping("/team/{teamName}/matches")
	 public List<Match> getMatchesforTeam(@PathVariable String teamName,@RequestParam int year){
		 LocalDate startDate = LocalDate.of(year,1,1);
		 LocalDate endDate = LocalDate.of(year+1,1,1);
		 return this.matchRepository.getMatchesByTeamBetweenDates(teamName,startDate,endDate);
	 }
 

}