/*									*
 *    ��ȭ ��ȣ ���� ���α׷� ���� ������Ʈ		*
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

class MenuChoiceException extends Exception	// �߸��� ������ ���� ó��
{
	int wrongChoice;
	
	public MenuChoiceException(int choice)
	{
		super("�߸��� ������ �̷������ϴ�");
		wrongChoice = choice;
	}
	public void showWrongChoice()
	{
		System.out.println(wrongChoice + "�� �ش��ϴ� ������ �������� �ʽ��ϴ�");
	}
}

class PhoneInfo	// ��ȭ��ȣ�� ������ Ŭ����
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

class PhoneBookManager	// Manager Ŭ���� (�Է�, �˻�, ����, �޴����)
{
	final int MAX_CNT = 3;
	PhoneInfo[] infoStorage = new PhoneInfo[MAX_CNT];	// �ִ� 100���� PhoneInfo ����
	int curCnt = 0;
	public static Scanner keyboard = new Scanner(System.in);
	
	private PhoneBookManager(){};	// PhoneBookManager �ν��Ͻ��� �ϳ��� �����ϱ� ���ؼ� private ������
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
			System.out.println("***** ������ �Է� *****");
			System.out.println("1. �Ϲ�  2. ����  3. ȸ��");
			System.out.print("����>> ");
			int choice = keyboard.nextInt();
			keyboard.nextLine();	// ���� ���ֱ�
			
			if(choice == INPUT_SELECT.NORMAL)
			{
				System.out.print("�̸� : ");	
				String name = keyboard.nextLine();
				System.out.print("��ȭ��ȣ : ");
				String phone = keyboard.nextLine();
				infoStorage[curCnt] = new PhoneInfo(name, phone);
				System.out.println("***** ������ �Է� �Ϸ� ***");
				curCnt ++;	
			}
			else if(choice == INPUT_SELECT.UNIV)
			{
				System.out.print("�̸� : ");	
				String name = keyboard.nextLine();
				System.out.print("��ȭ��ȣ : ");
				String phone = keyboard.nextLine();
				System.out.print("���� : ");
				String major = keyboard.nextLine();
				System.out.print("�г� : ");
				int year = keyboard.nextInt();
				keyboard.nextLine();	// ���� ���ֱ�
				infoStorage[curCnt] = new PhoneUnivInfo(name, phone, major, year);
				System.out.println("***** ������ �Է� �Ϸ� ***");
				curCnt ++;	
			}
			else if(choice == INPUT_SELECT.COMPANY)
			{
				System.out.print("�̸� : ");	
				String name = keyboard.nextLine();
				System.out.print("��ȭ��ȣ : ");
				String phone = keyboard.nextLine();
				System.out.print("ȸ�� : ");	
				String company = keyboard.nextLine();
				infoStorage[curCnt] = new PhoneCompanyInfo(name, phone, company);
				System.out.println("***** ������ �Է� �Ϸ� ***");
				curCnt ++;	
			}
			else
				throw new MenuChoiceException(choice);
			
		}
		else
			System.out.println("* ��ȭ��ȣ�ΰ� ����á���ϴ� *");

	}
	
	public void searchData()
	{
		System.out.println("***** ������ �˻� *****");
		
		System.out.print("�̸� : ");
		String name = keyboard.nextLine();
		
		int dataIdx = search(name);
		
		if(dataIdx >= 0)
		{
			infoStorage[dataIdx].showPhoneInfo();
			System.out.println("***** ������ �˻� �Ϸ� ***");
		}
		else
			System.out.println("* �ش� �����Ͱ� �������� �ʽ��ϴ�. *");
		
	}
	
	public void deleteData()
	{
		System.out.println("***** ������ ���� *****");

		System.out.print("�̸� : ");
		String name = keyboard.nextLine();
		
		int dataIdx = search(name);
		if(dataIdx >= 0)
		{
			for(int idx = dataIdx; idx < (curCnt-1); idx++)
				infoStorage[idx] = infoStorage[idx + 1];
			System.out.println("***** ������ ���� �Ϸ� ***");
			curCnt --;
		}
		else
			System.out.println("* �ش� �����Ͱ� �����ϴ� *");
	}
	
	public void showMenu()
	{
		System.out.println("���� �ϼ���...");
		System.out.println("1. ������ �Է� ");
		System.out.println("2. ������ �˻� ");
		System.out.println("3. ������ ���� ");
		System.out.println("4. ���α׷� ���� ");
		System.out.print("����>> ");
		
	
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

class PhoneBook	// ���� 
{

	public static void main(String[] args)
	{
		PhoneBookManager manager = PhoneBookManager.createManagerInst();
		int choice;
		System.out.println("***************");
		System.out.println("���α׷��� �����մϴ�");
		
		while(true)
		{
		
			try
			{
				manager.showMenu();
				choice = PhoneBookManager.keyboard.nextInt();
				PhoneBookManager.keyboard.nextLine();	// ���� �Է� ���ֱ�
				
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
					System.out.println("���α׷��� �����մϴ�.");
					return;
				}
			}
			catch(MenuChoiceException e)
			{
				System.out.println(e.getMessage());
				e.showWrongChoice();
				System.out.println("�޴� ������ ó������ �ٽ� �����մϴ�.\n");
			}
		}
	}
}



