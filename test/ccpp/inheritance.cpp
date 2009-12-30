#include <iostream.h>

class A
{

public:
	void disp()
	{
		cout<<"inside class A"<<endl;
	}
};

class B:public A
{
public:
	void disp()
	{
		cout<<"inside class B"<<endl;
	}

};


class C:public B
{
public:
	void disp()
	{
		cout<<"inside class C"<<endl;
	}

};

void main()
{
	A *a;

	a=new A	;
	a->disp();

	a=new B	;
	a->disp();

	a=new C	;
	a->disp();


	delete a;

}