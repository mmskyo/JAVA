import java.util.GregorianCalendar;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.*;

public class User {
	
	// 일정 출력 
	public void printSchedule(Schedule s) {
		System.out.println(s.getDate().get(Calendar.YEAR) + "년 " 
		+ s.getDate().get(Calendar.MONTH) + "월 " + s.getDate().get(Calendar.DAY_OF_MONTH)
		+ "일, 제목 : " + s.getTitle() + ", 일정 내용 : " + s.getNote()
		+ "우선순위 : " + s.getPriority() + "카테고리 : " + s.getCategory());
	}
	
	// 총 일정 출력 
	public void printSchedules(int[] indices) {
		int cnt = 0;
		ScheduleManager sm = null;
		Schedule[] schedules = sm.getSchedules();
		
		for(int element : indices) {
			System.out.println();
			System.out.println( "-" + ++cnt + "번째 검색 결과: ");
			printSchedule(schedules[element]);
			
			if(indices[cnt] == 0) break; 
		}
		
		System.out.println();
		System.out.println("총 " + (cnt) + "개의 검색 결과가 있습니다.");
	}
	
	// 날짜 입력받기 
	public void inputDate() throws Exception { 
		Scanner scan = new Scanner(System.in);
		System.out.println();
		System.out.println("시작 일시에 대한 정보를 입력해주세요.");
		System.out.print("년도(yyyy): ");
		int year = scan.nextInt();
		if(year < 2000) {
			throw new Exception();
		}
		
		System.out.print("월(MM): ");
		int month = scan.nextInt();
		if(month < 0 || month > 12) {
			throw new Exception("잘못된 범위입니다. 1월 부터 12월 사이중에서 입력해 주세요.");
		}
		
		System.out.print("일(dd): ");
		int day = scan.nextInt();
		if(month == 1 || month ==3 || month == 5 || month == 7 
				|| month == 8 || month == 10 || month == 12) 
		{
			if(day < 0 || day > 32) {
				throw new Exception("잘못된 범위입니다. 1일부터 31일 사이중에서 입력해 주세요.");
			}
		} 
		// 2월 ( 28일만 있는 경우 )
		else if( month == 2) {
			if(day < 0 || day > 29)
				throw new Exception("잘못된 범위입니다. 1일부터 28일 사이로 입력해주세요.");
		}
		// 30일까지만 있는 경우
		else {
			if(day < 0 || day > 31) {
				throw new Exception("잘못된 범위입니다. 1일부터 30일 사이로 입력해주세요.");
			}
		}
		// 그레고리안캘린더 형태로 저장 
		GregorianCalendar g = new GregorianCalendar(year, month, day);
	}
	
	// 제목 입력받기 
	public void inputTitle() throws Exception {
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
		System.out.print("일정 이름: ");
		String title = scan.nextLine();
		
	}
	
	// 설명 입력받기
	public void inputNote() throws Exception {
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
		System.out.print("메모: ");
        String note = scan.nextLine();
	}
	
	// 우선순위 입력받기 
	public void inputPriority() throws Exception {
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
    	System.out.print("중요도(1 이상부터):" );
        int priority = scan.nextInt();
        if(priority < 0) {
        	throw new Exception("1 이상의 양의 정수만 해주세요. ");
        }
	
	}
	
	//
	public void inputCategory() throws Exception {
		Schedule s = null;
		System.out.println("카테고리(공부 - y, 학교 - c, 일 - w, 가족 - f, 개인 - i) : ");
		Scanner scan = new Scanner(System.in);
		char category = scan.next().charAt(0);
		
	
		//카테고리 입력 검사
		boolean doesTypeMatch = false;
		for(char element: s.getCategories()) {
			if(category == element) {
				doesTypeMatch = true;
				break;
			}
		}
		if(doesTypeMatch == false) {
			throw new Exception();
		}
		
	}
	
	
	
	// 메인 함수 
	public static void main(String args[]) throws Exception{
		Scanner scan = new Scanner(System.in);
		ObjectOutputStream out = null;
		User user = new User();
		Schedule s = null;
		ScheduleManager sm = null;
		Schedule[] scheduleList = null;
		int index = 0;
		int[] indices = null;
		int searchOption = 0;

		GregorianCalendar date = null;
		int year = 0, month = 0, day = 0, priority = 0;
		String title = null, notes = null, categoryString = null;
		char category = 0;
		
		while(true) //반복문
		{
			// 미리 일정 추가하는 프로그램
			try {
	            sm.addSchedule(new Schedule(new GregorianCalendar(2023, 1, 2), "ex1", "e.g.", 1, 'i'));
	            sm.addSchedule(new Schedule(new GregorianCalendar(2024, 10, 18), "ex2", "e.g.", 3, 'i'));
	            sm.addSchedule(new Schedule(new GregorianCalendar(2024, 5, 29), "ex3", "e.g.", 2, 's'));
	        } catch (Exception e) {
	            System.out.println("미리 일정을 추가하는 부분에서 에러가 발생했습니다.");
	        }
			
			
			Menu.menu(); // 메뉴를 보여준다
			int menu = 0;
			while(true) {
				try{
					menu = scan.nextInt();	// 번호를 입력 받는다
					break;
				} catch (InputMismatchException e) {
					scan = new Scanner(System.in);
					System.out.println("잘못된 입력입니다. 다시 입력하세요.");
				}
			}
			
			switch(menu) // 번호에 따른 메소드 실행
			{
			case 1:
				// 일정 추가 
				System.out.println("일정을 입력하세요.(년, 월, 일, 제목, 내용, 우선순위, 카테고리)");
				while(true) {
					try {
						// 일정 입력받기 
						user.inputDate();
						
						System.out.println("제목 : ");
						title = scan.next();
						System.out.println("메모 : ");
						notes = scan.next();
						System.out.println("우선순위 : ");
						priority = scan.nextInt();
						System.out.println("카테고리(공부 - y, 학교 - c, 일 - w, 가족 - f, 개인 - i) : ");
						categoryString = scan.nextLine().trim();
						category = categoryString.charAt(0);
						
						date = new GregorianCalendar(year, month, day);
						
						// 일정 크기설정 
						sm = new ScheduleManager(100);
						
						// 입력받은 일정을 s에 넣기 
						s = new Schedule(date, title, notes, priority, category);
					
						// 일정이 겹칠 시에 (equals함수 사용)
						for(int i = 0; i < scheduleList.length; i++) {
							if(title.equals(scheduleList[i].getTitle()))
								throw new Exception("이미 존재하는 일정입니다.");
						}
						
						// 일정 추가 
						sm.addSchedule(s);
						break;
						
					} catch (InputMismatchException e) {
						scan = new Scanner(System.in);
						System.out.println("잘못된 입력입니다. 처음부터 다시 입력하세요.(월, 일, 제목, 내용, 우선순위, 카테고리)");
						System.out.println("일정을 입력하세요.");
					} catch (ArrayIndexOutOfBoundsException e) {
						System.out.println("일정을 저장할 수 있는 공간이 없습니다.");
						break;
					} catch (Exception e) {
						// getMessage() 함수를 통해 "잘못된 범위"인지 확인
						// 직접 getMessage출력 하지 말기 출력을 위해 쓰는게 아니니까 
						if(e.getMessage().contains("잘못된 범위")) {
							System.out.println("잘못된 범위입니다. 다시 입력해 주십시오.");
						}
						if(e.getMessage().contains("존재하는 일정")) {
							System.out.println("이미 존재하는 일정입니다. 추가를 계속 진행할까요? (y/n)");
							String yn = null;
							yn = scan.next();
							if(yn == "y") {
								// 일정 추가 
								sm.addSchedule(s);
							}
							else 
								System.out.println("추가를 중단하였습니다.");
								
						}
					}
				}
				break;
			
				// 검색 
			case 2:
				// 먼저 조건에 맞는 일정을 검색 후 결과를 보여주며 삭제할 번호를 고르라고 한다.
				
				// 우선은 해당 날짜 일정만
				System.out.println("해당 일정의 년, 월, 일을 입력해 주세요.");
				try {
					// 입력
					user.inputDate();
					GregorianCalendar g = new GregorianCalendar(year, month, day);
					
			    	// 검색
			    	indices = sm.searchIndices(g); 
			
			    	// 출력
					user.printSchedules(indices);
					
					if(indices.length == 0) {
						throw new Exception("삭제할 일정이 없습니다.");
					}
					
					// 입력
					System.out.print("몇 번째 일정을 삭제하겠습니까?: ");
					int deleteIndex;
					deleteIndex = scan.nextInt();
					
					// 일정 삭제
					int id = indices[deleteIndex-1];
					sm.deleteSchedule(id);
					System.out.println("일정을 삭제하였습니다.");
					
					break;
					
				} catch (InputMismatchException e) {
					scan = new Scanner(System.in);
					System.out.println("잘못된 입력입니다. 다시 입력하세요.");
				} catch (IndexOutOfBoundsException e) {
					System.out.println("더 자세한 조건으로 다시 검색해주십시오.");
				}
				
				break;
				
			case 4:
				// 일정 수정 
				System.out.println("일정을 수정하려면 먼저 검색을 수행해야합니다.");
				// 일정 검색
				System.out.println("검색 방법을 선택해 주세요.");
				System.out.println("1. 날짜");
				System.out.println("2. 기간");
				System.out.println("3. 제목/내용");
				System.out.println("4. 우선순위");
				System.out.println("5. 카테고리");
				
				// 입력
				searchOption = scan.nextInt();
				
				switch(searchOption) {
				case 1:
					// 일정 날짜 검색
					System.out.print("검색할 날짜를 입력하세요: ");
					
					// 일정 try범위 작게하고 exception을 범위마다 나눠야함
					// search에서 잘못되었는지 입력이 잘못되었는지 다른 데서인지 
					try {
						// 검색어 입력받기
						user.inputDate();
						
						GregorianCalendar g = new GregorianCalendar(year, month, day);
						
						indices = sm.searchIndices(g);
						
					} catch (NoSuchElementException e) {
						System.out.println("잘못된 입력입니다. 다시 검색해주십시오.");
						break;
					} catch (IndexOutOfBoundsException e) {
						System.out.println("다시 검색해주십시오.");
					}
					
					System.out.print("수정할 일정 번호를 입력하세요: ");
					// 일정 정보 출력 
					user.printSchedules(indices);
					
					int updateIndex;
					updateIndex = scan.nextInt();
					
					// 일정 수정
					user.inputDate();
					sm.updateSchedule(updateIndex, date);
					
					// 출력
					user.printSchedule(s);
					
					break;
					
				case 2:
					// 일정 기간 검색
					GregorianCalendar sg = null;
					GregorianCalendar eg = null;
					System.out.print("검색할 일정 기간을 입력하세요: ");
					try {
						// 검색어 입력받기
						int syear = scan.nextInt();
						int smonth = scan.nextInt();
						int sday = scan.nextInt();
						int eyear = scan.nextInt();
						int emonth = scan.nextInt();
						int eday = scan.nextInt();
						
						sg = new GregorianCalendar(syear, smonth, sday);
						eg = new GregorianCalendar(eyear, emonth, eday);
						
						indices = sm.searchIndices(sg, eg);
						
					} catch (NoSuchElementException e) {
						System.out.println("다시 검색해주십시오.");
						break;
					} catch (IndexOutOfBoundsException e) {
						System.out.println("더 자세한 조건으로 다시 검색해주십시오.");
					}
					
					
					System.out.print("수정할 일정 번호를 입력하세요: ");
					// 일정 정보 출력 
					user.printSchedules(indices);
					
					updateIndex = scan.nextInt();
					
					// 일정 수정
					sm.updateSchedule(updateIndex, sg, eg);
					
					// 출력
					user.printSchedule(s);
					break;
					
				case 3:
					
					// 일정 제목 검색
					System.out.print("검색할 일정 제목을 입력하세요: ");
					try {
						// 검색어 입력받기
							indices= sm.searchIndices(scan.next());
						
					} catch (NoSuchElementException e) {
						System.out.println("다시 검색해주십시오.");
						break;
					} catch (IndexOutOfBoundsException e) {
						System.out.println("더 자세한 조건으로 다시 검색해주십시오.");
					}
					
					
					System.out.print("수정할 일정 번호를 입력하세요: ");
					// 일정 정보 출력 
					user.printSchedules(indices);
					
					updateIndex = scan.nextInt();
					
					// 일정 수정
					user.inputTitle();
					sm.updateSchedule(updateIndex, title);
					
					// 출력
					user.printSchedule(s);
					break;
					
				case 4:
					
					// 일정 내용 검색
					System.out.print("검색할 일정 내용을 입력하세요: ");
					try {
						// 검색어 입력받기
							indices= sm.searchIndices(scan.next());
						
					} catch (NoSuchElementException e) {
						System.out.println("다시 검색해주십시오.");
						break;
					} catch (IndexOutOfBoundsException e) {
						System.out.println("더 자세한 조건으로 다시 검색해주십시오.");
					}
					
					
					System.out.print("수정할 일정 번호를 입력하세요: ");
					// 일정 정보 출력 
					user.printSchedules(indices);
					
					updateIndex = scan.nextInt();
					
					// 일정 수정
					System.out.println("수정할 내용을 입력하세요 : ");
					user.inputNote();
					
					sm.updateSchedule(updateIndex, notes);
					
					// 출력
					user.printSchedule(s);
					break;
					
				case 5:
					// 일정 우선순위 검색
					System.out.print("검색할 일정 우선순위를 입력하세요: ");
					try {
						// 검색어 입력받기
						indices = sm.searchIndices(scan.nextInt());
						
					} catch (NoSuchElementException e) {
						System.out.println("다시 검색해주십시오.");
					} catch (IndexOutOfBoundsException e) {
						System.out.println("더 자세한 조건으로 다시 검색해주십시오.");
					}
					
					
					System.out.print("수정할 일정 번호를 입력하세요: ");
					// 일정 정보 출력 
					user.printSchedules(indices);
					
					updateIndex = scan.nextInt();
					
					// 일정 수정
					user.inputPriority();
					sm.updateSchedule(updateIndex, priority);
					
					// 출력
					user.printSchedule(s);
					break;
					
				case 6:
					// 일정 카테고리 검색
					System.out.print("검색할 일정 카테고리를 입력하세요: ");
					try {
						// 검색어 입력받기
						indices = sm.searchIndices(scan.nextLine());
						
					} catch (NoSuchElementException e) {
						System.out.println("다시 검색해주십시오.");
					} catch (IndexOutOfBoundsException e) {
						System.out.println("더 자세한 조건으로 다시 검색해주십시오.");
					}
					
					
					System.out.print("수정할 일정 번호를 입력하세요: ");
					// 일정 정보 출력 
					user.printSchedules(indices);
					
					updateIndex = scan.nextInt();
					
					// 일정 수정
					user.inputCategory();
					category = sm.updateSchedule(updateIndex, category);
					
					// 출력
					user.printSchedule(s);
					break;
					
				}
				
			case 5:
				// 일정 검색
				System.out.println("검색 방법을 선택해 주세요.");
				System.out.println("1. 날짜");
				System.out.println("2. 기간");
				System.out.println("3. 제목/내용");
				System.out.println("4. 우선순위");
				System.out.println("5. 카테고리");
				
				// 입력
				searchOption = scan.nextInt();
				
				switch(searchOption) {
				case 1:
					// 일정 날짜 검색
					System.out.print("검색할 날짜를 입력하세요: ");
					
					try {
						// 검색어 입력받기
						year = scan.nextInt();
						month = scan.nextInt();
						day = scan.nextInt();
						GregorianCalendar g = new GregorianCalendar(year, month, day);
						
						indices = sm.searchIndices(g);
						
					} catch (NoSuchElementException e) {
						System.out.println("다시 검색해주십시오.");
						break;
					}
					
					
					// 일정 정보 출력 - 해당 스케줄에서 정보 반환 
					user.printSchedules(indices);
					break;
					
				case 2:
					// 일정 기간 검색
					System.out.print("검색할 일정 기간을 입력하세요: ");
					try {
						// 검색어 입력받기
						
						System.out.println("시작되는 연도 : ");
						int syear = scan.nextInt();
						
						System.out.println("시작되는 월 : ");
						int smonth = scan.nextInt();
						
						System.out.println("시작되는 일 : ");
						int sday = scan.nextInt();
						
						System.out.println("끝나는 연도 : ");
						int eyear = scan.nextInt();
						
						System.out.println("끝나는 월 : ");
						int emonth = scan.nextInt();
						
						System.out.println("끝나는 일 : ");
						int eday = scan.nextInt();
						
						GregorianCalendar sg = new GregorianCalendar(syear, smonth, sday);
						GregorianCalendar eg = new GregorianCalendar(eyear, emonth, eday);
						
						indices = sm.searchIndices(sg, eg);
						
					} catch (NoSuchElementException e) {
						System.out.println("다시 검색해주십시오.");
						break;
					}
					
					
					// 일정 정보 출력 - 해당 스케줄에서 정보 반환 
					user.printSchedules(indices);
					break;
					
				case 3:
					
					// 일정 제목/내용 검색
					System.out.print("검색할 일정 제목/내용을 입력하세요: ");
					try {
						// 검색어 입력받기
						indices = sm.searchIndices(scan.next());
						
					} catch (NoSuchElementException e) {
						System.out.println("다시 검색해주십시오.");
						break;
					}
					
					
					// 일정 정보 출력 - 해당 스케줄에서 정보 반환 
					user.printSchedules(indices);
					break;
					
				case 4:
					// 일정 우선순위 검색
					System.out.print("검색할 일정 우선순위를 입력하세요: ");
					try {
						// 검색어 입력받기
						indices = sm.searchIndices(scan.nextInt());
						
					} catch (NoSuchElementException e) {
						System.out.println("다시 검색해주십시오.");
						break;
					}
					
					
					// 일정 정보 출력 - 해당 스케줄에서 정보 반환 
					user.printSchedules(indices);
					break;
					
				case 5:
					// 일정 카테고리 검색
					System.out.print("검색할 일정 카테고리를 입력하세요: ");
					try {
						// 검색어 입력받기
						indices = sm.searchIndices(scan.nextLine());
						
					} catch (NoSuchElementException e) {
						System.out.println("다시 검색해주십시오.");
						break;
					}
					
					
					// 일정 정보 출력 - 해당 스케줄에서 정보 반환 
					user.printSchedules(indices);
					break;
					
				}
				
			case 6: 
				// 파일로 저장하기
				String fileName = scan.nextLine();
				ObjectOutputStream out1 = null;
				try {
					out1 = new ObjectOutputStream(new FileOutputStream("schedules.dat"));
					
					for(int cnt = 0; cnt < sm.getSchedulesCnt(scheduleList); cnt++) {
						out1.writeObject(scheduleList[cnt]);
					}
				} catch (IOException ioe) {
					
				} finally {
					try {
						out.close();
						}
						catch (Exception e) {
						}
				}
				
				break;
				
			case 7:
				//파일 읽어들이기
				ObjectInputStream in = null;
				try {
					in = new ObjectInputStream(new FileInputStream("schdules.dat"));
					
					while (true) {
						for(int cnt = 0; cnt < sm.getSchedulesCnt(scheduleList); cnt++)
							scheduleList[cnt] = (Schedule) in.readObject();
						}
					
					} catch (FileNotFoundException fnfe) {
						System.out.println("파일이 존재하지 않습니다.");
					} catch (EOFException eofe) {
						System.out.println("끝");
					} catch (IOException ioe) {
						System.out.println("파일을 읽을 수 없습니다.");
					} catch (ClassNotFoundException cnfe) {
						System.out.println("해당 클래스가 존재하지 않습니다.");
					} finally {
						try {
								in.close();
						} catch (Exception e) {
							}
					}
				break;
				
			case 8:
				// 프로그램 종료 안내 출력
				System.out.println("프로그램을 종료합니다.");
				break;
				
			default:
				// 숫자를 잘못 입력한 경우
				System.out.println("다시 입력하세요 "); 
				break;
				
			}
			
			if(menu == 6)
				break; //반복문 종료(프로그램 종료)
			}}}
				