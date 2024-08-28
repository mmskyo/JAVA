import java.util.GregorianCalendar;

class Schedule implements java.io.Serializable{
	private GregorianCalendar date = null;
	private int priority = 0;
	private String title, note = null;
	private char category = 0;
	private char[] categories = {'y', 'c', 'w', 's', 'i'};
	
	Schedule (GregorianCalendar date, String title, String note, int priority, char category) {
		this.date = date;
		this.title = title;
		this.note = note;
		this.priority = priority;
		this.category = category;
	}
	
	GregorianCalendar getDate() {
		return date;
	}
	
	String getTitle() {
		return title;
	}
	
	String getNote() {
		return note;
	}
	
	int getPriority() {
		return priority;
	}

	char getCategory() {
		return category;
	}
	
	public char[] getCategories() {
		return categories;
	}
	
	GregorianCalendar setDate() {
		return date;
	}
	
	String setTitle() {
		return title;
	}
	
	String setNote() {
		return note;
	}
	
	int setPriority() {
		return priority;
	}

	char setCategory() {
		return category;
	}
	
	// 일정 제목 기준으로 equals 메서드 재정의
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
        	return true;
        }      

        if (obj == null || getClass() != obj.getClass()) {
        	return false;
        }
        Schedule schedule = (Schedule) obj;{
        	return title != null ? title.equals(schedule.title) : schedule.title == null;
        }

    }
	
}
