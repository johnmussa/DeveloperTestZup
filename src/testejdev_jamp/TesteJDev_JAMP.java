package testejdev_jamp;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author JohnMussa
 */
public class TesteJDev_JAMP {
    public static void main(String[] args) throws IOException {
     
    PrintWriter foutput = new PrintWriter("output.txt", "UTF-8");
    BufferedReader br = new BufferedReader(new FileReader("input.txt"));
    Integer plateau_coord_x, plateau_coord_y,rover_pos_x = 0,rover_pos_y = 0;
    String[] plateau_coord_s,rover_coord_s;
    Boolean read_coord = true;
    String rover_facing = "";
    Integer rover_facing_n = 0;
    String output = "";
    Boolean rover_not_move = false, rover_not_move_all = false;
    List<String> cardinal_points = new ArrayList<>();
    Collections.addAll(cardinal_points,"N","S","W","E");
      
    try {
        // First input line
        plateau_coord_s = br.readLine().split("\\s");
        
        if(plateau_coord_s.length != 2){
            output = output.concat("Invalid plateau upper right coordinates.");
            rover_not_move_all = true;    
        }
        plateau_coord_x = Integer.parseInt(plateau_coord_s[0]);
        plateau_coord_y = Integer.parseInt(plateau_coord_s[1]);
        
        if(plateau_coord_x == 0 && plateau_coord_y == 0){
            output = output.concat("Invalid plateau upper right coordinates.");
            rover_not_move_all = true;    
        }
        
        // Deployed rovers info
        String line = br.readLine();
        while (line != null && !rover_not_move_all) {
            if(read_coord){
                //Reading Rover coordinates
                rover_coord_s = line.split("\\s");

                if(rover_coord_s.length != 3){
                    output = output.concat("Invalid rover info.");
                    rover_not_move = true;
                }else{
                rover_pos_x = Integer.parseInt(rover_coord_s[0]);
                rover_pos_y = Integer.parseInt(rover_coord_s[1]);
                rover_facing = rover_coord_s[2]; 
                }
                
                if(!(cardinal_points.contains(rover_facing)) && !rover_not_move){ //direction different than N, S, W or E
                    output = output.concat("Invalid rover direction.");
                    rover_not_move = true;
                }else{
                    switch (rover_facing){
                        case "N":
                            rover_facing_n = 0;
                            break;
                        case "E":
                            rover_facing_n = 1;
                            break;
                        case "S":
                            rover_facing_n = 2;
                            break;
                        case "W":
                            rover_facing_n = 3;
                            break;   
                    }
                }
                
                read_coord = false;
            }else{
                //Reading rover movement
                if(!rover_not_move){
                    for (char movement_action: line.toCharArray()){
                        switch(movement_action){
                            case 'R':
                                if(rover_facing_n == 3)
                                    rover_facing_n = 0;
                                else
                                    rover_facing_n++;
                                break;

                            case 'L':
                                if(rover_facing_n == 0)
                                    rover_facing_n = 3;
                                else
                                    rover_facing_n--;
                                break;

                            case 'M':
                                if(rover_facing_n == 0){
                                    if((rover_pos_y +1) <= plateau_coord_y)
                                        rover_pos_y++;
                                }
                                if(rover_facing_n == 1){
                                    if(rover_pos_x +1 <= plateau_coord_x)
                                        rover_pos_x++;
                                }
                                if(rover_facing_n == 2){
                                    if(rover_pos_y -1 >= 0)
                                        rover_pos_y--;
                                }
                                if(rover_facing_n == 3){
                                    if(rover_pos_x -1 >= 0)
                                        rover_pos_x--;
                                }
                                break;

                            default:
                                output = output.concat("Invalid rover movement.");
                        }
                    }

                    switch (rover_facing_n){
                        case 0:
                            rover_facing = "N";
                            break;
                        case 1:
                            rover_facing = "E";
                            break;
                        case 2:
                            rover_facing = "S";
                            break;
                        case 3:
                            rover_facing = "W";
                            break;   
                    }

                    output = output.concat(rover_pos_x.toString()+" "+rover_pos_y.toString()+" "+rover_facing);
                    }
                   
                foutput.println(output);
                output = "";
                read_coord = true;
                rover_not_move = false;
                rover_pos_x = 0;
                rover_pos_y = 0;
            }
            line = br.readLine();
        }
    } finally {
        br.close();
        foutput.close();
    }
    
    if (rover_not_move_all)
        foutput.println(output);
        
    }
}
