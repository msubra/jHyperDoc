namespace DnWebPoll
{
	using System;
    using System.Data;
    using System.Drawing;
	using System.Web;
	using System.Web.UI.WebControls;
	using System.Web.UI.HtmlControls;
    using System.Data.SqlClient;
    using System.Text;
    using System.Collections;

    //----------------------------------------------------------------------------------------------------------------//
    // WebPoll in C# by Gordon F. Weis - Please do not remove
    //----------------------------------------------------------------------------------------------------------------//
    // 07/14/2001 - First release as a User Control. If you enhance the 
    // code or find problems and make improvements, please send me a 
    // message so that I can keep the code up-to-date.
    //----------------------------------------------------------------------------------------------------------------//
    // My email address:    gfw@yyyZ.net
    // My web site address: yyyZ.Net    
    //----------------------------------------------------------------------------------------------------------------//
    // A working copy is available at http://72t.Net
    //----------------------------------------------------------------------------------------------------------------//
    // Source at http://www.ASPFree.com
    //----------------------------------------------------------------------------------------------------------------//

	public abstract class WebPoll : System.Web.UI.UserControl
	{
        protected System.Web.UI.WebControls.PlaceHolder Ph;
        protected string       m_connString;
        protected int          m_whichPoll;
        protected Label        m_Label;
        protected string	   CookieName;
        protected string	   CookieValue;
        protected ArrayList    RdoValues;       // Array of Checkboxes
        protected ArrayList    alAnswers;       // Array of Question Answers
        protected PollQuestion pQ;
		protected System.Web.UI.WebControls.PlaceHolder phWebPoll;              // Class Defined Below
        protected PollAnswer   pA;              // Class Defined Below

        /// <summary>
        /// Variable passed from calling page - See Default.aspx
        /// Which Poll is being usedl 
        /// </summary>
        public int whPoll
        {   get { return m_whichPoll; }
            set { m_whichPoll = value; }
        }

        /// <summary>
        /// Variable passed from calling page - See Default.aspx
        /// The database connection string
        /// </summary>
        public string connString
        {
            get{ return m_connString; }
            set { m_connString = value; }
        }
	
		/// <summary>
		public WebPoll()
		{
			this.Init += new System.EventHandler(Page_Init);
		}


		private void Page_Init(object sender, EventArgs e)
		{
			InitializeComponent();
		}
        
        /// <summary>
        /// Start by looking for a Cookie so that you don't get two
        /// responses from the same computer.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Page_Load(object sender, System.EventArgs e)
        {
            // Cookie is the PollID 
            CookieName   = "POLL";
            CookieValue  = "PollId" + m_whichPoll.ToString();
            // Get the location of the PlaceHolder Control
            Ph = (PlaceHolder)this.FindControl("phWebPoll"); 
            
            // Assume that you will show the Poll
            if(MkControls()) 
            {
                 HttpCookie Cookie = Request.Cookies[CookieName];
                 if (Cookie!=null)
                    {
                        // If Cookie is found, only show the results
                        if(Cookie.Value.Equals(CookieValue)) ShowResults();
                    }
            }
        }

		///		Required method for Designer support - do not modify
		///		the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			this.Load += new System.EventHandler(this.Page_Load);

		}
    
        /// <summary>
        /// Record a Response...........................................
        /// Check to see which answer button was selected and update the 
        /// record. After update, hide panel1 and display panel2 write a 
        /// cookie to the users computer.
        /// </summary>
        /// <param name="sender"> </param>
        /// <param name="e"> </param>
        public void Btn_Click (object sender, System.EventArgs e)
        {
            HtmlInputRadioButton m_Rdo;
            // Walk through all of the buttons
            for (int i=0; i<RdoValues.Count; i++) 
            {
                // Get Each button in the array
                m_Rdo = (HtmlInputRadioButton)RdoValues[i];	
                if (m_Rdo.Checked) // This is the response
                {
                    alAnswers        = pQ.alAnswers;
                    pA               = (PollAnswer)alAnswers[i];
                    pA.Count        += 1;   // Update the Response Count
                    pQ.TotalAnswers += 1;   // Increment Total Count
                    pA.UpdAnswer(m_connString); // Results to File

                    // Create and Write Cookie		
                    HttpCookie Cookie = new HttpCookie(CookieName,CookieValue);
                    Cookie.Expires    = DateTime.Now.AddDays(30);
                    Response.Cookies.Add(Cookie);
                }
            }
            ShowResults(); // Now Show the results
        }

        /// <summary>
        /// Creates a table and inserts the Poll Questions and the
        /// submt button
        /// </summary>
        /// <returns></returns>
        protected bool MkControls()
        {
            HtmlTable				m_Table;
            HtmlTableRow			m_Row;
            HtmlTableCell			m_Cell;
            HtmlInputRadioButton    m_Rdo;
            Button					m_Btn;
			
            //Set the table properties
            m_Table				=	new HtmlTable();
            m_Table.Border		=	1;
            m_Table.CellPadding	=	1; 
            m_Table.CellSpacing	=	0; 
            m_Table.BorderColor =	"#000000";
            m_Table.BgColor		=	"#FFFFEE"; 
            m_Table.Width		=	"160px";

            // Get Poll Question
            pQ = new PollQuestion(m_whichPoll,m_connString);
            
            // If there isn't a valid Poll then exit
            if (pQ.Found!=true) return false;
            
            // Get the Question Answers
            alAnswers = pQ.alAnswers;

            // First Table Row for Question
            m_Row  = new HtmlTableRow();
            m_Cell = new HtmlTableCell();
            m_Cell.ColSpan   = 2;
            m_Cell.Align     = "center";
            m_Cell.InnerHtml = "<font size='1' face='arial'><b>"+pQ.Question+"</b></font>";
            m_Row.Cells.Add(m_Cell);
            m_Table.Rows.Add(m_Row);

            // Poll Answers
            RdoValues = new ArrayList();		// New Array for Poll Answers
            for (int i=0;i<pQ.NumberAnswers;i++)
            {
                pA				= (PollAnswer)alAnswers[i];
                m_Row           = new HtmlTableRow(); 

                // This cell shows the checkbox
                m_Rdo			= new HtmlInputRadioButton();
                m_Rdo.ID		= String.Format("R{0}",i);
                m_Rdo.Name		= "G1"; // Group Name
                RdoValues.Add(m_Rdo);		
                m_Cell	        = new HtmlTableCell();
                m_Cell.Align	= "center";
                m_Cell.Controls.Add(m_Rdo);
                m_Row.Cells.Add(m_Cell);

                // This cell shows the Answer
                m_Cell = new HtmlTableCell();
                m_Cell.InnerHtml = "&nbsp<font size='1' face='arial'>"+pA.Answer+"</font>";
                m_Row.Cells.Add(m_Cell);

                // Add the Row to the table
                m_Table.Rows.Add(m_Row);
            }

            // Show Previous Number of Responses
            m_Row			= new HtmlTableRow();
            m_Cell			= new HtmlTableCell();
            m_Cell.ColSpan	= 2;
            m_Cell.Align    = "center";
            m_Label  	    = new Label();
            m_Label.Text   += string.Format("<font size='1' face='arial'>{0} Responses</font>",pQ.TotalAnswers);
            m_Cell.Controls.Add(m_Label);
            m_Row.Cells.Add(m_Cell);
            m_Table.Rows.Add(m_Row);

            // Add the Submit Button 
            m_Row			= new HtmlTableRow();
            m_Cell			= new HtmlTableCell();
            m_Cell.ColSpan	= 3;
            m_Cell.Align    = "center";
            m_Btn		    = new Button();
            m_Btn.Click	   += new System.EventHandler (this.Btn_Click);
            m_Btn.Text      = "Submit";
            m_Btn.ForeColor = Color.White;
            m_Btn.BackColor = Color.DarkBlue;
            m_Btn.Font.Bold = true;
            m_Btn.Font.Name = "Arial";
            m_Btn.Font.Size = 8;

            m_Cell.Controls.Add(m_Btn);
            m_Row.Cells.Add(m_Cell);
            m_Table.Rows.Add(m_Row);
            Ph.Controls.Add(m_Table);  // Add the Poll Table
            return true;
        }

        /// <summary>
        /// Show the Poll Results after submission or if
        /// the Poll was previously answered
        /// </summary>
        protected void ShowResults()
        {
            HtmlTable				m_Table;
            HtmlTableRow			m_Row;
            HtmlTableCell			m_Cell;

            // Clear the Panel for the Show Results Table ----------------
            Ph.Controls.Clear();
			
            //Set the table properties -----------------------------------
            m_Table             = new HtmlTable();
            m_Table.Border      = 1;
            m_Table.CellPadding = 1; 
            m_Table.CellSpacing = 0; 
            m_Table.BorderColor = "#000000";
            m_Table.BgColor     = "#FFFFEE"; 
            m_Table.Width       = "160px";

            // Get Answers
            alAnswers           = pQ.alAnswers;

            // First Table Row for Question ------------------------------
            m_Row  = new HtmlTableRow();
            m_Cell = new HtmlTableCell();
            m_Cell.ColSpan   = 2;
            m_Cell.Align     = "center";
            m_Cell.InnerHtml = "<font size='1' face='arial'><b>"+pQ.Question+"</b></font>";
            m_Row.Cells.Add(m_Cell);
            m_Table.Rows.Add(m_Row);

            for (int i=0;i<pQ.NumberAnswers;i++)
            {
                pA				= (PollAnswer)alAnswers[i];
                m_Row           = new HtmlTableRow(); 

                // This cell shows the Answer
                m_Cell = new HtmlTableCell();
                m_Cell.InnerHtml = "<font size='1' face='arial'>"+pA.Answer+"</font>";
                m_Row.Cells.Add(m_Cell);

                // This cell shows the percentage results
                m_Cell = new HtmlTableCell();
                if (pQ.TotalAnswers>0) m_Cell.InnerHtml = String.Format("<font size='1' face='arial'>{0:f0}%</font>",(double)pA.Count/(double)pQ.TotalAnswers*100.0);
                 else                  m_Cell.InnerHtml = "<font size='1' face='arial'>0%</font>";
                m_Cell.Align="right";
                m_Row.Cells.Add(m_Cell);
                m_Table.Rows.Add(m_Row);
            }

            m_Row			= new HtmlTableRow();
            m_Cell			= new HtmlTableCell();
            m_Cell.ColSpan	= 2;
            m_Cell.Align    = "center";
            m_Label  	    = new Label();
            m_Label.Text   += string.Format("<font size='1' face='arial'>{0} Responses</font>",pQ.TotalAnswers);
            m_Cell.Controls.Add(m_Label);
            m_Row.Cells.Add(m_Cell);
            m_Table.Rows.Add(m_Row);

            Ph.Controls.Add(m_Table);  // Add the Poll Table
        }

        // Answers to Questions ========================================================
        /// <summary>
        /// Class definition for a single answer
        /// </summary>
        public class PollAnswer 
        {
            private int    m_Id;           // Database Id
            private int    m_PollId;       // Database Id of PollQuestion
            private int    m_Count;        // Number of Responses
            private string m_Answer;       // The Answer 
            
            public int Id
            {
                get { return m_Id;   }
                set { m_Id = value; }
            }   

            public int PollId
            {
                get { return m_PollId;   }
                set { m_PollId = value; }
            }   
            public int Count
            {
                get { return m_Count;   }
                set { m_Count = value; }
            }   
            public string Answer
            {
                get { return m_Answer;   }
                set { m_Answer = value; }
            }  

            /// <summary>
            /// Upates the Count Variable
            /// </summary>
            public void UpdAnswer( string cnStr) 
            {
                StringBuilder sb = new StringBuilder();
                sb.Append("UPDATE aPollAnswers ");
                sb.Append("SET ");
                sb.Append("AnswerCount = @m_Count ");
                sb.Append("WHERE Id = @m_Id ");
                string InsertString = sb.ToString();

                SqlConnection m_Connection = new SqlConnection(cnStr);
                SqlCommand    m_SqlCommand = new SqlCommand(InsertString, m_Connection);
                m_SqlCommand.Parameters.Add(new SqlParameter("@m_Id",SqlDbType.Int));
                m_SqlCommand.Parameters["@m_Id"].Value = m_Id;
                m_SqlCommand.Parameters.Add(new SqlParameter("@m_Count",SqlDbType.Int));
                m_SqlCommand.Parameters["@m_Count"].Value = m_Count;
                try 
                {
                    m_Connection.Open();
                    m_SqlCommand.ExecuteNonQuery();
                    m_Connection.Close();
                }
                catch (Exception e)
                {
                    throw e;
                }
            } //  public void UpdAnswer() 
        } //  public class PollAnswer 


        // Poll Question Class =====================================================

        /// <summary>
        /// A class definition for the PollQuestion
        /// </summary>
        public class PollQuestion 
        {
            private int			 m_Id;			// Database Id
            private int			 m_Active;		// Is it an Active Question 
            private string		 m_Question;	// The Question
            private DateTime	 m_Start;		// Starting Date
            private DateTime	 m_End;			// Ending Date 
            private bool		 m_Found;		// Was the Question Found
            private ArrayList	 m_alAnswers;	// Array of Answers 
            private int          m_NuAnswers;	// Number of Answer Elements
            private int          m_TotalAnswers;// Number of Prev. Responses

            public int Id
            {
                get { return m_Id;   }
                set { m_Id = value; }
            }   
            public int Active
            {
                get { return m_Active;   }
                set { m_Active = value; }
            }   

            public DateTime StartDate
            {
                    get { return m_Start;   }
                set { m_Start = value; }
            }   
            public DateTime EndDate
            {
                    get { return m_End;   }
                set { m_End = value; }
            }   
            public bool Found
            {
                    get { return m_Found;   }
                set { m_Found = value; }
            }   
            public string Question
            {
                    get { return m_Question;   }
                set { m_Question = value; }
            }  

            /// <summary>
            /// The Answers to the Question saved in an array
            /// </summary>
            public ArrayList alAnswers
            {
                    get { return m_alAnswers;   }
                set { m_alAnswers = value; }
            }   

            /// <summary>
            /// Number of Answers in this Poll
            /// </summary>
            public int NumberAnswers
            {
               get { return m_NuAnswers;   }
                set { m_NuAnswers = value; }
            }   

            /// <summary>
            /// Total Number of Previous Poll Answers - Not Saved
            /// </summary>
            public int TotalAnswers
            {
                get { return m_TotalAnswers;   }
                set { m_TotalAnswers = value; }
            }   

            /// <summary>
            /// Get Poll Question from Database 
            /// </summary>
            /// <param name="pId"></param>
            /// <param name="cnStr"></param>
            public PollQuestion(int pId, string cnStr)
            {   
                string   sql,Query;
                // Query based on the Poll Id AND the Beginning and Ending Dates
                sql     = "SELECT * FROM aPollQuestions WHERE '{0}' BETWEEN StartDate And EndDate AND Id={1}";
                Query   = String.Format(sql,DateTime.Now,pId);
                m_Found = false;

                // Open the connection, retrieve the data and bind the recordset
                SqlConnection   m_Connection = new SqlConnection(cnStr);
                SqlCommand		m_SqlCommand = new SqlCommand(Query,m_Connection);

                try 
                {	// Open the Connection ---------------------------- 
                    m_Connection.Open();
                    // Create DataReader ------------------------------
                    SqlDataReader m_SqlDataReader = m_SqlCommand.ExecuteReader();                
                    // Get 1st Row - true if record found -------------
                    if (m_SqlDataReader.Read()) 
                    {   m_Id       = Convert.ToInt32(m_SqlDataReader["Id"].ToString());
                        m_Active   = Convert.ToInt32(m_SqlDataReader["Active"].ToString());
                        m_Start    = Convert.ToDateTime(m_SqlDataReader["StartDate"].ToString());
                        m_End      = Convert.ToDateTime(m_SqlDataReader["EndDate"].ToString());
                        m_Question = m_SqlDataReader["Question"].ToString();
                        m_Found    = true;
                        alAnswers  = new ArrayList();
                        GetAnswers( cnStr ); // Get Answers for Poll
                    }
                    m_SqlDataReader.Close(); // Close DataReader
                    m_Connection.Close();    // Close Connection
                } // try
                catch (Exception e)
                {
                    throw e;
                    // Label1.Text = e.ToString();
                } // catch
            } // public PollQuestion(int pId)

            /// <summary>
            /// This function is called by public PollQuestion(int pId) 
            /// after it is successfull in retreiving the Question
            /// Retreives the Answers to the Poll and places them
            /// in an arraylist that is a private member of the class
            /// </summary>
            protected void GetAnswers(string cnStr)
            {
                string sql,Query;
                sql   = "SELECT * FROM aPollAnswers WHERE PollID={0} ";
                Query = String.Format(sql, m_Id);

                SqlConnection  m_Connection     = new SqlConnection(cnStr);
                SqlDataAdapter m_SqlDataAdapter = new SqlDataAdapter(Query,m_Connection);
                try 
                {
                    DataSet Ds = new DataSet();
                    m_SqlDataAdapter.Fill(Ds,"Answers");
                    PollAnswer pA;
                    m_TotalAnswers = 0;
                    m_NuAnswers    = Ds.Tables["Answers"].Rows.Count;
                    // Place each answer in an array
                    for (int i=0;i<m_NuAnswers;i++)
                    {   pA			    = new PollAnswer();
                        pA.Id			= Convert.ToInt32(Ds.Tables["Answers"].Rows[i]["Id"].ToString());
                        pA.Count	    = Convert.ToInt32(Ds.Tables["Answers"].Rows[i]["AnswerCount"].ToString());
                        pA.Answer		= Ds.Tables["Answers"].Rows[i]["Answer"].ToString();
                        pA.PollId	    = m_Id;
                        m_TotalAnswers += pA.Count;
                        m_alAnswers.Add( pA );		
                    }
                    m_Connection.Close();
                }
                catch (Exception e)
                {
                    throw e;
                }
            }
        } // public class Question
    }
}
