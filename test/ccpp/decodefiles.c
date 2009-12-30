#include <stdio.h>
int main()
{
FILE *fp,*f;
char buf[2000];
int i=0;
fp=fopen("pass.txt","r");

if(fp==NULL)
{
 fprintf(stderr,"Error Opening File");
 exit(1);
}
f=fopen("pass1.txt","w");
for(i=0;!feof(fp);i++)
{
 fgets(buf,i,fp);
 fprintf(f,"%s",buf);
fprintf(stdout,"%s",buf);
}
printf("\n\n%d",i);

getch();
}
