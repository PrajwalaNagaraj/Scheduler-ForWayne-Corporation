import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.regex.Pattern;

class risingCity {
    //Main utility to read from file,  handle construction and delegate min heap and RB tree insert, printNodes logic
    static HeapNode heap_inst = null;

    public static void main(String args[]) throws IOException {
        //Logic to find the total construction time for all input buildings
        BufferedReader read_maxTimeBuf = new BufferedReader(new FileReader(args[0]));
        String cur_line = null;
        long max_time = 0;
        boolean isWayneConstruction = false;
        while ((cur_line = read_maxTimeBuf.readLine()) != null) {
            if (cur_line.matches("(?i:.*insert.*)"))
                max_time += Long.parseLong(cur_line.split(",")[1].split("\\)")[0]) + Long.parseLong(cur_line.split(":")[0]);
        }
        read_maxTimeBuf.close();
        //buffer to read the input file line by line through the maxtime counter
        BufferedReader read_inputLBL = new BufferedReader(new FileReader(args[0]));
        List<Long> arrival_time_clash = new ArrayList<>();
        List<String> print_deleting_building = new ArrayList<>();
        int execute_for_5 = 0, construction_factor = 0;
        String line = read_inputLBL.readLine();
        int Wayne_buildingID1, Wayne_buildingID2;
        Building_Handler bh_obj = new Building_Handler();
        long cur_time_index = 0;
        int total_time = 0, p = 0, executionTime = 0;
        long arrival_time = Integer.parseInt(line.split(":")[0]);
        String command = line.split(" ")[1];
        String input_str = (command.split("\\("))[0];
        String values = (command.split("\\("))[1];

        for (cur_time_index = 0; cur_time_index <= max_time; cur_time_index++) {
            if (arrival_time == cur_time_index) {
                if (input_str.matches("(?i:.*insert.*)")) {
                    Wayne_buildingID1 = Integer.parseInt(values.split("\\)")[0].split(",")[0]);
                    total_time = Integer.parseInt(values.split("\\)")[0].split(",")[1]);
                    bh_obj.Insert(Wayne_buildingID1, total_time, executionTime);
                }
                if (input_str.matches("(?i:.*PrintBuilding.*)") && arrival_time_clash.contains(arrival_time)) {
                    if (values.split("\\)")[0].split(",").length == 1) {
                        Wayne_buildingID1 = Integer.parseInt(values.split("\\)")[0].split(",")[0]);
                        bh_obj.Print_WayneB(Wayne_buildingID1);
                    }
                    if (values.split("\\)")[0].split(",").length == 2) {
                        Wayne_buildingID1 = Integer.parseInt(values.split("\\)")[0].split(",")[0]);
                        Wayne_buildingID2 = Integer.parseInt(values.split("\\)")[0].split(",")[1]);
                        bh_obj.Print_WayneBInRange(Wayne_buildingID1, Wayne_buildingID2);
                    }
                }

                if ((line = read_inputLBL.readLine()) != null) {
                    arrival_time = Integer.parseInt(line.split(":")[0]);
                    command = line.split(" ")[1];
                    input_str = (command.split("\\("))[0];
                    if (input_str.equalsIgnoreCase("PrintBuilding")) {
                        arrival_time_clash.add(arrival_time);
                        print_deleting_building.add("(" + values);
                    }
                    values = (command.split("\\("))[1];
                }
                //writing all outputs to file
                bh_obj.WritetoFile();
            }
            //picking new building for construction if no construction taking place
            if (construction_factor == 0) {
                if (bh_obj.heap.isEmpty()) {
                    continue;
                } else {
                    heap_inst = bh_obj.heap.extractMin();
                    isWayneConstruction = false;
                    execute_for_5 = 0;
                    construction_factor = 1;
                }
            }
            //picking building with least construction time.
            if (construction_factor == 1) {
                execute_for_5++;
                heap_inst.heap_key += 1;
                heap_inst.rb_Node.WayneB_exe_time += 1;
                if (heap_inst.rb_Node.WayneB_exe_time == heap_inst.WayneB_completion_timeMH) {
                    if (arrival_time_clash.contains(cur_time_index + 1)) {
                        int index = arrival_time_clash.indexOf(cur_time_index + 1);
                        if (print_deleting_building.get(index).split("\\)")[0].split(",").length == 2) {
                            Wayne_buildingID1 = Integer.parseInt(values.split("\\)")[0].split(",")[0]);
                            Wayne_buildingID2 = Integer.parseInt(values.split("\\)")[0].split(",")[1]);
                            arrival_time_clash.remove(index);
                            print_deleting_building.remove(index);
                            bh_obj.Print_WayneBInRange(Wayne_buildingID1, Wayne_buildingID2);

                        } else {
                            //removing deleting buildings after printing
                            Wayne_buildingID1 = Integer.parseInt(values.split("\\)")[0].split(",")[0]);
                            arrival_time_clash.remove(index);
                            print_deleting_building.remove(index);
                            bh_obj.Print_WayneB(Wayne_buildingID1);

                        }
                    }
                    isWayneConstruction = true;
                    bh_obj.print_completed_buildings(heap_inst.rb_Node.ID, (cur_time_index + 1));
                    bh_obj.WritetoFile();
                    bh_obj.rbTree.delete(heap_inst.rb_Node);

                    if (execute_for_5 != 5) {
                        execute_for_5 = 0;
                        construction_factor = 0;
                    }
                } else {
                    isWayneConstruction = false;
                }

                if (execute_for_5 == 5) {
                    execute_for_5 = 0;
                    if (!isWayneConstruction) {
                        bh_obj.heap.insert(heap_inst.WayneB_completion_timeMH, heap_inst.heap_key, heap_inst.rb_Node);
                    }
                    construction_factor = 0;
                }
            }
        }
        read_inputLBL.close();
    }
}
