package proj.fivesecrule;

import java.util.ArrayList;
import java.util.List;

public class TeamChanger {
    private int NumberOfTeams;
    private List<String> TeamNames;
    private int[] TeamPoints;
    private int MaxPoints;
    private int PlayingTeam = 0;
    private int PreviousTeam = -1;

    public TeamChanger(int number_of_teams, List<String> team_names, int max_points){
        this.NumberOfTeams = number_of_teams;
        this.TeamNames = team_names;
        this.MaxPoints = max_points;
        TeamPoints = new int[NumberOfTeams];
    }

    public void increase_points(){
        TeamPoints[PlayingTeam]++;
    }

    public void decrease_points(){
        if (TeamPoints[PlayingTeam] != 0)
            TeamPoints[PlayingTeam] -= 1;
    }

    public void change_playing_team(){
        PreviousTeam = PlayingTeam;
        if (PlayingTeam != NumberOfTeams-1)
            PlayingTeam++;
        else {
            PlayingTeam = 0;
        }
    }

    public void increase_points_to_previous_team(){
        TeamPoints[PreviousTeam]++;
    }

    public void decrease_points_to_previous_team(){
        if (TeamPoints[PreviousTeam] != 0)
            TeamPoints[PreviousTeam] -= 1;
    }

    public String Generate_winning_teams(){
        List<String> WinningTeams = new ArrayList<>();
        for (int i = 0; i<TeamPoints.length; i++){
            if (TeamPoints[i] == MaxPoints)
                WinningTeams.add(TeamNames.get(i)+" ");
        }
        StringBuilder str = new StringBuilder();
        for (int i =0; i<WinningTeams.size(); i++)
            str.append(WinningTeams.get(i));
        return str.toString();
    }

    public boolean Time_to_finish(){
        boolean finish = false;
        if (PlayingTeam == NumberOfTeams-1)
            for (int i = 0; i<TeamPoints.length; i++){
                if (TeamPoints[i] == MaxPoints)
                    {
                        finish = true;
                        break;
                    }
            }
        return finish;
    }

    public String get_team_name(){
        return TeamNames.get(PlayingTeam);
    }

    public int get_team_points(){
        return TeamPoints[PlayingTeam];
    }

    public String get_previous_team_name() {
        return TeamNames.get(PreviousTeam);
    }

    public boolean ifPreviousTeamAvailable(){
        return PreviousTeam >= 0;
    }

    public String get_all_point(){
        String points = "";
        for (int i = 0; i<TeamNames.size(); i++)
        {
            points += TeamNames.get(i) + " : " + TeamPoints[i] + "\n";
        }
        return points;
    }
}
