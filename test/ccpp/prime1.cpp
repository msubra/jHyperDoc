#include <iostream.h>
#include <afx.h>
#include <conio.h>

class prime
{

private:
	long x,y,carry,j,i,l,k;
public:
CString add(CString  a,CString  b)
{
	CString c;
	carry=0;

	
	i=a.GetLength();
	j=b.GetLength();

	l=i;

	l=i>j?i:j;

	if(i>j)
		insert(b,i-j,"0",1);
	else
		if(j>i)
			insert(a,j-i,"0",1);

	for(i=l;i>0;i--)
	{
		j = a.GetAt(i-1)-'0' + b.GetAt(i-1)-'0' + carry;
		carry = j / 10;
		j = j % 10;
 		c+=j+'0';
	}

	if(carry>0)
		c+=carry+'0';
	c.MakeReverse();
	return c;
}


CString mul(CString a,CString b)
{
	
	CString c[20001],d;
	long i,k;
	char v;

	i=j=k=carry=x=y=0;
  
	x=a.GetLength();
	y=b.GetLength();
	
	if(y<x)
		swapValue(a,b);

	x=a.GetLength();
	y=b.GetLength();
	i=y-1;

	for(;i>=0;)
	{
		
   		carry=0;
		v=b.GetAt(i);
		
		for(j=x;j>0;j--)
		{
			k = (a.GetAt(j-1)-'0')*(v-'0')+carry;
			carry = k /10;
			k = k % 10;
			c[i]+=(k+'0');
   		}
		if(carry>0)
			c[i]+=carry+'0';

		c[i].MakeReverse();

		insert(c[i],b.GetLength()-(i+1),"0",0);

		d=add(d,c[i]);

		c[i].Empty();
	   	i--;
	}
	return d;
}

void insert(CString &a,int count,CString c,int pos)
{

	


	switch(pos)
	{
	case 1:
		{
			CString temp;
			temp=a;
			a.Empty();

			for(;count>0;count--)
				a+=c;
			a+=temp;
			break;
		}

	case 0:
		{
			for(;count>0;count--)
				a+=c;
			break;

		}
	}
}


void swapValue(CString &a,CString &b)
{
	CString temp;
	temp=a;
	a=b;
	b=temp;
}

CString power(CString a,int count)
{
	CString t("1");
	FILE *fp;
	int i=count;
	fp=fopen("pr1.doc","w");

	if(fp==NULL)
		exit(1);

	for(;count>0;count--)
	{
		t=mul(t,a);
		fprintf(fp,"%s\n",t);
		printf("2^%d...Processing\r",i-count);
	}
	fclose(fp);
	return t;
}

};
void main()
{

	CString a,b;
	time_t begin;
	int i;
	prime c;
	
	cout<<"Enter :"<<endl;
	cin>>i;

	begin=time(0);
	cout<<c.power("2",i)<<endl;
	printf("Time Taken:%ld seconds\n",time(0)-begin);

	getch();
}	
