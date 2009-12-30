Imports System.ComponentModel
Imports System.Drawing
Imports System.Windows.Forms
Imports System.IO

Public Class Form1
    Inherits System.Windows.Forms.Form

    Public Sub New()
        MyBase.New

        Form1 = Me

        'This call is required by the Win Form Designer.
        InitializeComponent

        'TODO: Add any initialization after the InitializeComponent() call
    End Sub

    'Form overrides dispose to clean up the component list.
    Public Overloads Sub Dispose()
        MyBase.Dispose()
        components.Dispose()
    End Sub

#Region " Windows Form Designer generated code "

    'Required by the Windows Form Designer
    Private components As System.ComponentModel.Container
    Private WithEvents Button1 As System.Windows.Forms.Button

    Dim WithEvents Form1 As System.Windows.Forms.Form

    'NOTE: The following procedure is required by the Windows Form Designer
    'It can be modified using the Windows Form Designer.  
    'Do not modify it using the code editor.
    Private Sub InitializeComponent()
        Me.components = New System.ComponentModel.Container()
        Me.Button1 = New System.Windows.Forms.Button()

        '@design Me.TrayHeight = 0
        '@design Me.TrayLargeIcon = False
        '@design Me.TrayAutoArrange = True
        Button1.Location = New System.Drawing.Point(64, 72)
        Button1.Size = New System.Drawing.Size(88, 24)
        Button1.TabIndex = 0
        Button1.Text = "Read"

        Me.Text = "Form1"
        Me.AutoScaleBaseSize = New System.Drawing.Size(5, 13)

        Me.Controls.Add(Button1)
    End Sub

#End Region

    Private Function ReadTextFile(ByVal sFileName As String) As String

        Dim s As String

        Try
            Dim oFile As FileStream = New FileStream(sFileName, FileMode.Open, FileAccess.Read, FileShare.Read)
            Dim oReader As StreamReader = New StreamReader(oFile)

            s = oReader.ReadToEnd

            oReader.Close()
            oFile.Close()

            ReadTextFile = s

        Catch

            ReadTextFile = "Unable to open file."

        End Try

    End Function

    Private Sub Button1_Click(ByVal sender As Object, ByVal e As System.EventArgs) Handles Button1.Click
        Dim s As String

        s = ReadTextFile(Environment.CurrentDirectory & "\ReadMe.txt")

        MessageBox.Show(s)
    End Sub
End Class
