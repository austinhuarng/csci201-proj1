package austinhu_CSCI201L_Assignment1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.MalformedParametersException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String fileName = "";
		FileReader inputFile;
		double lati, longi;
		Gson gson = new Gson();
		BufferedReader br = null;
		Restaurant restaurant = null;
		//ask for restaurant file
		while (true){    
			try{
				System.out.print("What is the name of the restaurant file?");
				fileName = sc.nextLine();
				inputFile = new FileReader(fileName);
				File f = new File(fileName);
				try {
					String canonical = f.getCanonicalPath();
					Path p = Paths.get(canonical);
					String properfile = p.getFileName().toString();
					System.out.println(fileName);
					System.out.println(properfile);
					if(!fileName.equals(properfile)){
						throw new InputMismatchException();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try{
					br = new BufferedReader(new FileReader(fileName));
					restaurant = gson.fromJson(br, Restaurant.class);
				} catch (FileNotFoundException e) {
					System.out.println("File not found.");
				}
				for(int i=0; i<restaurant.data.size(); i++){
					if(restaurant.data.get(i).getAddress().isEmpty()){
						throw new MalformedParametersException();
					}
					if(restaurant.data.get(i).getLatitude() == 0 ||
							restaurant.data.get(i).getLongitude() == 0){
						throw new MalformedParametersException();
					}
					if(restaurant.data.get(i).getMenu().isEmpty()){
						throw new MalformedParametersException();
					}
					if(restaurant.data.get(i).getName().isEmpty()){
						throw new MalformedParametersException();
					}
				}
				System.out.println("The file has been properly read.");
				break;
			}catch (FileNotFoundException e){
				System.out.println("The file " + fileName + " could not be found.");
			}catch (JsonParseException e) { 
				System.out.println("The file " + fileName + " is not formatted properly.");
			}catch (NumberFormatException e){
				System.out.println("This file has mismatched parameters");
			}catch(MalformedParametersException e){
				System.out.println("This file has missing or malformed parameters");
			}catch(NullPointerException e){
				System.out.println("This file has missing or malformed parameters");
			}catch(InputMismatchException e){
				System.out.println("File name is case sensitive");
			}
		}

		//ask for latitude and longitude
		while(true){
			try{
				System.out.print("What is your latitude?");
				lati = Double.parseDouble(sc.nextLine());
				if(lati < -90 || lati > 90)
					throw new Exception();
				break;
			}catch (Exception e){
				System.out.println("Please Enter a Valid Latitude");
			}	
		}
		while(true){
			try{
				System.out.print("What is your longitude?");
				longi = Double.parseDouble(sc.nextLine());
				if(longi < -180 || longi > 180)
					throw new InputMismatchException();
				break;
			}catch(InputMismatchException e){
				System.out.println("Please Enter a Valid Longitude");

			}
		}
		for(int i=0; i<restaurant.data.size(); i++){
			restaurant.data.get(i).distance = restaurant.findDist(lati, restaurant.data.get(i).getLatitude(), longi, restaurant.data.get(i).getLongitude());
		}
		while(true){
			System.out.println("1) Display all restaurants");
			System.out.println("2) Search for a restaurant");
			System.out.println("3) Search for a menu item");
			System.out.println("4) Add a new restaurant");
			System.out.println("5) Remove a restaurant");
			System.out.println("6) Sort restaurants");	
			System.out.println("7) Exit");
			int choice;
			while(true){
				try {
					System.out.println("What would you like to do?");
					choice = Integer.parseInt(sc.nextLine());
					if(choice < 1 || choice > 7)
						throw new NumberFormatException();
					break;
				} catch (NumberFormatException e) {
					System.out.println("That is not a valid option.");
				}		
			}
			if(choice == 1){
				for(int i=0; i<restaurant.data.size(); i++){
					System.out.println(restaurant.data.get(i).getName()
							+ ", located "
							+ restaurant.data.get(i).distance
							+ " miles away at "
							+ restaurant.data.get(i).getAddress());				
				}
				if(restaurant.data.isEmpty()){
					System.out.println("No restaurants available");
				}
			}else if(choice == 2){
				boolean found = false;
				while(!found){
					String resto = "";
					try{
						System.out.print("What is the name of the restaurant you would like to search for?");
						resto = sc.nextLine();
						for(int i=0; i<restaurant.data.size(); i++){
							if(restaurant.data.get(i).getName().toLowerCase().equals(resto.toLowerCase())){
								System.out.println(restaurant.data.get(i).getName()
										+ ", located "
										+ restaurant.findDist(lati, restaurant.data.get(i).getLatitude(), longi, restaurant.data.get(i).getLongitude())
										+ " miles away at "
										+ restaurant.data.get(i).getAddress());
								found = true;
								break;
							}
						}
						if(found==false){
							throw new InputMismatchException();
						}
					}catch (InputMismatchException e){
						System.out.println(resto + " could not be found.");
					}
				}				
			}else if (choice == 3) {
				boolean hasitem = false;
				int numitems;
				String item = "";
				while(true){
					try {
						System.out.print("What menu item would you like to search for?");
						item = sc.nextLine();
						for(int i=0; i<restaurant.data.size(); i++){
							numitems = 0;
							for(int j=0; j<restaurant.data.get(i).getMenu().size(); j++){
								if(restaurant.data.get(i).getMenu().get(j).toLowerCase().contains(item.toLowerCase())){
									numitems++;
									if(numitems > 1){
										System.out.print(" and " + restaurant.data.get(i).getMenu().get(j));
									}else{
									System.out.print(restaurant.data.get(i).getName() + " serves "
											+ restaurant.data.get(i).getMenu().get(j));
									}
									hasitem = true;
								}
								if(j == restaurant.data.get(i).getMenu().size()-1){
									System.out.println(".");
								}
							}
						}
						if(hasitem){
							break;
						}
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("No restaurant nearby serves " + item);
					}
				}
			}else if (choice == 4) {
				String newentry = "";
				String address = "";
				Double newlat, newlong;
				ArrayList<String> newmenu = new ArrayList<String>();
				Datum add_resto = null;
				while(true){
					try {
						System.out.print("What is the name of the restaurant you would like to add?");
						newentry = sc.nextLine();
						for(int i=0; i<restaurant.data.size(); i++){
							if(newentry.toLowerCase().equals(restaurant.data.get(i).getName().toLowerCase())){
								throw new Exception();
							} break;
						}
						add_resto = new Datum();
						add_resto.setName(newentry);
						break;
					} catch (Exception e) {
						System.out.println("There is already an entry for " + newentry);
					}
				}
				System.out.print("What is the address of " + newentry + "?");
				address = sc.nextLine();
				add_resto.setAddress(address);				
				while(true){
					try{
						System.out.print("What is the latitude of " + newentry + "?");
						newlat = Double.parseDouble(sc.nextLine());
						if(newlat < -90 || newlat > 90)
							throw new Exception();
						add_resto.setLatitude(newlat);
						break;
					}catch (Exception e){
						System.out.println("Please Enter a Valid Latitude");
					}	
				}
				while(true){
					try{
						System.out.print("What is the longitude of " + newentry + "?");
						newlong = Double.parseDouble(sc.nextLine());
						if(newlong < -180 || newlong > 180)
							throw new Exception();
						add_resto.setLongitude(newlong);
						break;
					}catch (Exception e){
						System.out.println("Please Enter a Valid Latitude");
					}	
				}
				System.out.print("What does " + newentry + " serve?");
				newmenu.add(sc.nextLine());
				while(true){
					System.out.println("Does " + newentry + " serve anything else?");
					System.out.println("1) Yes");
					System.out.println("2) No");
					if(sc.nextLine().equals("1")){
						System.out.print("What does " + newentry + " serve?");
						newmenu.add(sc.nextLine());
						continue;
					}else break;
				}
				restaurant.data.add(add_resto);
				System.out.println("There is now a new entry for "
						+ newentry
						+ ", located " + restaurant.findDist(lati, newlat, longi, newlong)
						+ " miles away at " + address);
				System.out.print(newentry + " serves ");
				for(int i=0; i<newmenu.size()-1; i++){
					System.out.print(newmenu.get(i) + ", ");
				}
				System.out.println("and " + newmenu.get(newmenu.size()-1) + ".");
			}else if (choice == 5) {
				if(restaurant.data.isEmpty()){
					System.out.println("No restaurants available");
					continue;
				}
				for(int i=0; i<restaurant.data.size(); i++){
					System.out.println((i+1) + ") " + restaurant.data.get(i).getName());
				}
				int remove;
				while(true){
					try{
						System.out.println("Which restaurant would you like to remove?");
						remove = Integer.parseInt(sc.nextLine());
						if(remove <=0 || remove > restaurant.data.size())
							throw new InputMismatchException();
						break;
					}catch (InputMismatchException e){
						System.out.println("Please Enter a Valid Number");
					}
				}
				restaurant.data.remove(remove-1);
			}else if (choice == 6) {
				int howsort;
				while(true){
					try{
						System.out.println("1) A to Z");
						System.out.println("2) Z to A");
						System.out.println("3) Closest to farthest");
						System.out.println("4) Farthest to closest");
						System.out.print("How would you like to sort by?");
						howsort = Integer.parseInt(sc.nextLine());
						if(howsort <=0 || howsort > 4)
							throw new Exception();
						break;
					}catch (Exception e){
						System.out.println("Please Enter a Valid Number");
					}	
				}
				if(howsort == 1){
			        Collections.sort(restaurant.data, new Sortbyname()); 
					System.out.println("Your restaurants are now sorted from A to Z");
				}else if (howsort == 2) {
					Comparator<Datum> c = Collections.reverseOrder(new Sortbyname()); 
			        Collections.sort(restaurant.data, c); 
					System.out.println("Your restaurants are now sorted from Z to A");
				}else if (howsort == 3) {
					Collections.sort(restaurant.data, new Sortbydistance()); 
					System.out.println("Your restaurants are now sorted from closest to farthest");
				}else if (howsort == 4){
					Comparator<Datum> c = Collections.reverseOrder(new Sortbydistance()); 
			        Collections.sort(restaurant.data, c); 
					System.out.println("Your restaurants are now sorted from furthest to closest");
				}
			}else if (choice == 7) {
				int saveornot;
				while(true){
					try{
						System.out.println("1) Yes");
						System.out.println("2) No");
						System.out.print("Would you like to save your edits?");
						saveornot = Integer.parseInt(sc.nextLine());
						if(saveornot <=0 || saveornot > 2)
							throw new Exception();
						break;
					}catch (Exception e){
						System.out.println("Please Enter a Valid Number");
					}	
				}
				if(saveornot == 2){
					System.out.println("Thank you for using my program!");
					return;
				}else if(saveornot == 1){
					try (FileWriter writer = new FileWriter(fileName)) {
			            gson.toJson(restaurant, writer);
			            System.out.println("Your edits have been saved to " + fileName + ".");
			        } catch (IOException e) {
						System.out.println("Couldn't write to file");
			        }
				}
				System.out.println("Thank you for using my program!");
				return;
			}
		}
	}

}
