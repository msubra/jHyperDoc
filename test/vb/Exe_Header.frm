VERSION 5.00
Begin VB.Form Form1 
   Caption         =   "Form1"
   ClientHeight    =   3540
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   6615
   LinkTopic       =   "Form1"
   ScaleHeight     =   3540
   ScaleWidth      =   6615
   StartUpPosition =   3  'Windows Default
   Begin VB.CommandButton Command2 
      Caption         =   "Command2"
      Height          =   495
      Left            =   4560
      TabIndex        =   3
      Top             =   840
      Width           =   1575
   End
   Begin VB.TextBox Text1 
      BeginProperty Font 
         Name            =   "Tahoma"
         Size            =   8.25
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   375
      Left            =   4320
      TabIndex        =   2
      Text            =   "0"
      Top             =   120
      Width           =   1695
   End
   Begin VB.ListBox List1 
      Height          =   1815
      Left            =   120
      TabIndex        =   1
      Top             =   120
      Width           =   4095
   End
   Begin VB.CommandButton Command1 
      Caption         =   "Command1"
      Height          =   375
      Left            =   120
      TabIndex        =   0
      Top             =   2040
      Width           =   1455
   End
End
Attribute VB_Name = "Form1"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Dim Fso As New FileSystemObject

Dim d As New Dictionary

Private Type ExeStructure
    ID As String * 4
    tbls  As Integer
    tsectors As Integer
End Type

Private Sub Command1_Click()
Dim Fname As String
Dim tempFile As File
Dim Free As Integer
Dim Data As String

Dim DataVal(520) As String * 1
Dim DataValHex(520) As Variant
Dim i As Integer
Dim size As Double

Dim s As ExeStructure

Fname = "D:\Cfiles\Debug\s.exe"
'Fname = "G:\Downloads\win32kb.exe"

Set tempFile = Fso.GetFile(Fname)

Text1 = ""
Free = FreeFile
i = 0
Open Fname For Binary As #Free
    While i < 512
        Get Free, , DataVal(i)
        
        d.Add Hex(i), Asc(DataVal(i))
        If Not IsNumeric(Hex(Asc(DataVal(i)))) Then
            DataValHex(i) = Asc(DataVal(i))
        Else
            DataValHex(i) = Format(Asc(DataVal(i)), "00")
        End If
        
        List1.AddItem "Address:" & Hex(i) & Space(5) & DataValHex(i)
        i = i + 1
    Wend
Close #Free

s.ID = Hex(DataValHex(0)) & Hex(DataValHex(1))
s.tbls = Val(DataValHex(2) + Val(DataValHex(3)))
s.tsectors = Val(DataValHex(4) + Val(DataValHex(5)))

End Sub

Private Sub Command2_Click()
MsgBox d.Item(Hex(Text1.Text))
End Sub

Private Sub List1_Click()
Text1.Text = List1.ListIndex + 1
End Sub
