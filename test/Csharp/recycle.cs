using System.IO;
using System.Windows.Forms;
class Recycle
{
public static void Main()
{
   int c=0;
      foreach(string s1 in Directory.GetFiles("c:\\recycled"))
       {
           ++c;
       }
if(c>0)
  {
       DialogResult r=MessageBox.Show("Are you sure EmptyRecycleBin ?","Message from C#",MessageBoxButtons.YesNo,MessageBoxIcon.Question);
       int ss=(int)r;
       if(ss==6)
           {
                foreach(string s in Directory.GetFiles("c:\\recycled"))
               {
                   File.Delete(s);
               }
           }
   }
}
}
