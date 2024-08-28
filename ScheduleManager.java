//import java.util.Calendar;
import java.io.File;
import java.util.GregorianCalendar;

public class ScheduleManager {
	private int count = 0;
	private Schedule[] scheduleList;
	
	// 기본 생성자 함수 
	ScheduleManager() {
		
	}
	
	// 생성자 함수 
	ScheduleManager(int listLength) {
		scheduleList = new Schedule[listLength];
	}
	
	// 일정 추가 함수 - 배열로 선언 
	void addSchedule(Schedule s) {
		scheduleList[count++] = s;
		// 자동으로 익셉션 생성 index out of bound으로 받음 
	}
	
	Schedule[] getSchedules() {
		return scheduleList;
	}
	
	/* 검색함수 오버라이딩 */
	
	// 검색 - 날짜 이후 
	int[] searchIndices(GregorianCalendar g) {
		
		int indices[] = new int[scheduleList.length];
		int index = 0;
		
		for(int i = 0; i < scheduleList.length; i++) {
			if( scheduleList[i] == null ) break;
			if( scheduleList[i].getDate().compareTo(g) > 0) {
				indices[index++] = i;
			}
		}
		return indices;
	}
	
	// 검색 - 시작일, 종료일 
	int[] searchIndices(GregorianCalendar start, GregorianCalendar end) {
		int indices[] = new int[scheduleList.length];
		int index = 0;
		
		for(int i = 0; i < scheduleList.length; i++) {
			if( scheduleList[i] == null ) break;
			
			if( scheduleList[i].getDate().compareTo(start) >= 0 
					&& scheduleList[i].getDate().compareTo(end) <= 0) {
				indices[index++] = i;
			}
		}
		return indices;
	}
	
	
	// 검색 - 일치 (제목, 내용)
	int[] searchIndices(String s) {
		int indices[] = new int[scheduleList.length];
		int index = 0;
		
		for(int i = 0; i < scheduleList.length; i++) {
			if( scheduleList[i] == null ) break;
			
			if(scheduleList[i].getTitle().contains(s) 
					|| scheduleList[i].getNote().contains(s)) {
				indices[index++] = i;
			}
		}
		return indices;
	}
	
	// 검색 - 우선순위 
	int[] searchIndices(int priority) {
		int indices[] = new int[scheduleList.length];
		int index = 0;
		
		for(int i = 0; i < scheduleList.length; i++) {
			if( scheduleList[i] == null ) break;
			
			if(scheduleList[i].getPriority() == priority) {
				indices[index++] = i;
			}
		}
		return indices;
	}
	
	// 검색 - 카테고리 
	int[] searchIndices(char category) {
		int indices[] = new int[scheduleList.length];
		int index = 0;
		
		for(int i = 0; i < scheduleList.length; i++) {
			if( scheduleList[i] == null ) break;
			
			if(scheduleList[i].getCategory() == category) {
				indices[index++] = i;
			}
		}
		return indices;
	}
	

	// 일정 삭제 함수
	void deleteSchedule(int index) {
		int size = 100;
		for(int i = index; i <scheduleList.length; i++) {
			if(i+1 == size) break; 
			scheduleList[i] = scheduleList[i+1];
		}
	}
	
	
	// 일정 수정 함수
	GregorianCalendar updateSchedule(int index, GregorianCalendar g) {
		return g;
	}
	
	GregorianCalendar updateSchedule(int index, GregorianCalendar sg, GregorianCalendar eg) {
		return sg;
	}
	
	String updateTitle(int index, String title) {
		return title;
	}
	
	String updateSchedule(int index,String note) {
		return note;
	}
	
	int updateSchedule(int index, int priority) {
		return priority;
	}
	
	char updateSchedule(int index, char category) {
		return category;
	}
	
	// 파일 읽어오기 함수
	File readSchedules(File f) {
		return f;
	}
	
	// 파일 저장하기 함수
	File writeSchedules(File f) {
		return f;
	}
	
	int getSchedulesCnt(Schedule[] scheduleList) { 
		int cnt = 0;
		for (int i = 0; i < scheduleList.length; i++) {
			if(scheduleList[i] == null ) break; 
			cnt++;
		}
		return cnt;
	}

}