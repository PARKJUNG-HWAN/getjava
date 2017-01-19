/*									*
 *    전화 번호 관리 프로그램 구현 프로젝트		*
 *									*/

import java.util.Scanner;

interface INIT_MENU
{
	int INPUT = 1, SEARCH = 2, DELETE = 3, EXIT = 4;
}

interface INPUT_SELECT
{
	int NORMAL = 1, UNIV = 2, COMPANY = 3;
}

class MenuChoiceException extends Exception	// 잘못된 선택의 예외 처리
{
	int wrongChoice;
	
	public MenuChoiceException(int choice)
	{
		super("잘못된 선택이 이뤄졌습니다");
		wrongChoice = choice;
	}
	public void showWrongChoice()
	{
		System.out.println(wrongChoice + "에 해당하는 선택은 존재하지 않습니다");
	}
}

class PhoneInfo	// 전화번호부 데이터 클래스
{
	String name;
	String phoneNumber;

	
	public PhoneInfo(String name, String num)	
	{
		this.name = name;
		phoneNumber = num;
	}
	
	public void showPhoneInfo()
	{
		System.out.println("name : " + name);
		System.out.println("phone : " + phoneNumber);
	}
}

class PhoneUnivInfo extends PhoneInfo
{
	String major;
	int year;
	
	public PhoneUnivInfo(String name, String num, String major, int year)
	{
		super(name, num);
		this.major = major;
		this.year = year;
	}
	
	public void showPhoneInfo()
	{
		super.showPhoneInfo();
		System.out.println("major : " + major);
		System.out.println("year : " + year);
	}
}

class PhoneCompanyInfo extends PhoneInfo
{
	String company;
	public PhoneCompanyInfo(String name, String num, String company)
	{
		super(name, num);
		this.company = company;
	}
	
	public void showPhoneInfo()
	{
		super.showPhoneInfo();
		System.out.println("company : " + company);
	}
	
}

class PhoneBookManager	// Manager 클래스 (입력, 검색, 삭제, 메뉴출력)
{
	final int MAX_CNT = 3;
	PhoneInfo[] infoStorage = new PhoneInfo[MAX_CNT];	// 최대 100개의 PhoneInfo 저장
	int curCnt = 0;
	public static Scanner keyboard = new Scanner(System.in);
	
	private PhoneBookManager(){};	// PhoneBookManager 인스턴스를 하나만 생성하기 위해서 private 생성자
	static PhoneBookManager inst = null;
	public static PhoneBookManager createManagerInst()
	{
		if(inst == null)
			inst = new PhoneBookManager();
		return inst;
	}
	
	public void inputData() throws MenuChoiceException
	{
		if(curCnt < MAX_CNT)
		{
			System.out.println("***** 데이터 입력 *****");
			System.out.println("1. 일반  2. 대학  3. 회사");
			System.out.print("선택>> ");
			int choice = keyboard.nextInt();
			keyboard.nextLine();	// 엔터 없애기
			
			if(choice == INPUT_SELECT.NORMAL)
			{
				System.out.print("이름 : ");	
				String name = keyboard.nextLine();
				System.out.print("전화번호 : ");
				String phone = keyboard.nextLine();
				infoStorage[curCnt] = new PhoneInfo(name, phone);
				System.out.println("***** 데이터 입력 완료 ***");
				curCnt ++;	
			}
			else if(choice == INPUT_SELECT.UNIV)
			{
				System.out.print("이름 : ");	
				String name = keyboard.nextLine();
				System.out.print("전화번호 : ");
				String phone = keyboard.nextLine();
				System.out.print("전공 : ");
				String major = keyboard.nextLine();
				System.out.print("학년 : ");
				int year = keyboard.nextInt();
				keyboard.nextLine();	// 엔터 없애기
				infoStorage[curCnt] = new PhoneUnivInfo(name, phone, major, year);
				System.out.println("***** 데이터 입력 완료 ***");
				curCnt ++;	
			}
			else if(choice == INPUT_SELECT.COMPANY)
			{
				System.out.print("이름 : ");	
				String name = keyboard.nextLine();
				System.out.print("전화번호 : ");
				String phone = keyboard.nextLine();
				System.out.print("회사 : ");	
				String company = keyboard.nextLine();
				infoStorage[curCnt] = new PhoneCompanyInfo(name, phone, company);
				System.out.println("***** 데이터 입력 완료 ***");
				curCnt ++;	
			}
			else
				throw new MenuChoiceException(choice);
			
		}
		else
			System.out.println("* 전화번호부가 가득찼습니다 *");

	}
	
	public void searchData()
	{
		System.out.println("***** 데이터 검색 *****");
		
		System.out.print("이름 : ");
		String name = keyboard.nextLine();
		
		int dataIdx = search(name);
		
		if(dataIdx >= 0)
		{
			infoStorage[dataIdx].showPhoneInfo();
			System.out.println("***** 데이터 검색 완료 ***");
		}
		else
			System.out.println("* 해당 데이터가 존재하지 않습니다. *");
		
	}
	
	public void deleteData()
	{
		System.out.println("***** 데이터 삭제 *****");

		System.out.print("이름 : ");
		String name = keyboard.nextLine();
		
		int dataIdx = search(name);
		if(dataIdx >= 0)
		{
			for(int idx = dataIdx; idx < (curCnt-1); idx++)
				infoStorage[idx] = infoStorage[idx + 1];
			System.out.println("***** 데이터 삭제 완료 ***");
			curCnt --;
		}
		else
			System.out.println("* 해당 데이터가 없습니다 *");
	}
	
	public void showMenu()
	{
		System.out.println("선택 하세요...");
		System.out.println("1. 데이터 입력 ");
		System.out.println("2. 데이터 검색 ");
		System.out.println("3. 데이터 삭제 ");
		System.out.println("4. 프로그램 종료 ");
		System.out.print("선택>> ");
		
	
	}
	
	private int search(String name)
	{
		for(int idx = 0; idx < curCnt; idx++)
		{
			PhoneInfo curInfo = infoStorage[idx];
			if(name.compareTo(curInfo.name) == 0)
				return idx;
		}
		return -1;
	}
	
}

class PhoneBook	// 메인 
{

	public static void main(String[] args)
	{
		PhoneBookManager manager = PhoneBookManager.createManagerInst();
		int choice;
		System.out.println("***************");
		System.out.println("프로그램을 시작합니다");
		
		while(true)
		{
		
			try
			{
				manager.showMenu();
				choice = PhoneBookManager.keyboard.nextInt();
				PhoneBookManager.keyboard.nextLine();	// 엔터 입력 없애기
				
				if(choice < INIT_MENU.INPUT || choice > INIT_MENU.EXIT)
					throw new MenuChoiceException(choice);
				
				switch(choice)
				{
				case INIT_MENU.INPUT:
					manager.inputData();
					break;
				case INIT_MENU.SEARCH:
					manager.searchData();
					break;
				case INIT_MENU.DELETE:
					manager.deleteData();
					break;
				case INIT_MENU.EXIT:
					System.out.println("프로그램을 종료합니다.");
					return;
				}
			}
			catch(MenuChoiceException e)
			{
				System.out.println(e.getMessage());
				e.showWrongChoice();
				System.out.println("메뉴 선택을 처음부터 다시 진행합니다.\n");
			}
		}
	}
}



