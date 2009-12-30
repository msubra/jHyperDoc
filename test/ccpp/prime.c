#include <stdio.h>
#include <math.h>

int checkprime(int num);

int main()
{
 int no=0,no_prime=0,val=0,pr=0,c=2;
unsigned long double mul_prime=1;
 printf("Enter No.Of Primes To Generate:");
 scanf("%d",&no);
printf("1.\t2\n");
printf("2.\t3\n");
 while(no_prime<(no-2))
{
 val++;
 pr=checkprime(val);
  if(pr==1)
  {
   no_prime++;
   c++;

   printf("%d.\t",c);
   printf("%d\n",val);
   mul_prime*=val;
  }
}
mul_prime*=6;
mul_prime++;
printf("\n\nThe Largest Prime Form Calculation=%f\n\n",mul_prime);


getch();
}

int checkprime(int num)
{
 int j,c,prime;

for(j=2;j<=sqrt(num);j++)
 {  c=num%j;
     if(c!=0)
         prime=1;
    else
     if(c==0)
     {
       prime=0;
       break;
     }

  }
 return(prime);
}



