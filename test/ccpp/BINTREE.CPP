#include <iostream.h>
#include <stdlib.h>
#include <conio.h>

typedef double Data;

class Node {
#define NO_NODE -1
public:

      Node *left,*right;
      Data data;

      Node (Node*,Node*,Data);
      void  getNode(Data&);
};

class BTree {

public:
       BTree();
       ~BTree();

       /*creation*/
       Node* getNode(Node*,Node*,Data);
       void addNode(Data);

       /* demolishion*/
       void delNode(Node*);
       void delSubTree(Node*);
       void delTree(Node*);

       /* traversing */
       void preOrder();
       void postOrder();
       void inOrder();

       int  getLevel(Node*);
	   int  getLevelCount();
       int  getCount();
	   Node* getRoot();
       Node* getInfo(Data);
       int isRootNode(Node* n);

       /* printing */
       void printNode(Node*);
       void printSubTree(Node*);
       void printTree(Node*);
       void printLevel(int);

private:
	Node *root;
	Data data;
	void _pre(Node*);
	void _post(Node*);
	void _in(Node*);
	void BTree::_printlevel(Node*,int);
	int count;
};

Node::Node(Node *left,Node *right,Data data)
	:data(data),left(left),right(right){
}

void Node::getNode(Data &d){
	d=data;
}

BTree::BTree(){		/*Creates a root node*/
	root=NULL;
	data=0;
	count=0;
}

BTree::~BTree(){	/*deletes entire binary tree*/
	delTree(root);
	root=NULL;
	data=0;
}

void BTree::delNode(Node* n){	/*deletes a node*/
	count--;
	delete n;  /* deleting a node */
}

void BTree::delSubTree(Node* subTree){	/*deletes a sub tree*/
	if ( subTree != NULL)
	{
		delNode(subTree->left);
		delNode(subTree->right);
		delNode(subTree);
	}
}

void BTree::delTree(Node* Tree){      /*deletes entire tree*/
	delSubTree(Tree);
}

Node* BTree::getNode(Node* left,Node* right,Data data) {
	Node *node;

	node = new Node(left,right,data);

	if ( node == NULL) {
		cout<<"Error Creating Node in getNode!"<<endl;
		exit(1);
	}

	return node;
}

void BTree::addNode(Data data) {
	Node *current,*parent,*newNode;

	current=root;
	parent=NULL;

	while ( current != NULL ) {
		parent = current;

		if ( data < current->data )
			current = current->left;
		else
			current = current->right;
	}

	newNode = getNode(NULL,NULL,data);

	if ( parent == NULL)
		root = newNode;
	else if( data < parent->data )
		parent->left = newNode;
	else
		parent->right = newNode;

	count++;	/* added a new node*/
}

void BTree::_pre(Node* n) {
	if( n != NULL) {
		printNode(n);
		_pre(n->left);
		_pre(n->right);
	}
}

void BTree::_post(Node* n) {
	if( n != NULL) {
		_post(n->left);
		printNode(n);
		_pre(n->right);
	}
}

void BTree::_in(Node* n) {
	if( n != NULL) {
		_in(n->left);
		_in(n->right);
		printNode(n);
	}
}

void BTree::preOrder() {
	_pre(root);
}

void BTree::postOrder() {
	_post(root);
}

void BTree::inOrder() {
	_in(root);
}

Node* BTree::getInfo(Data data) {
	Node* cur;

	cur = root;

	while ( cur != NULL){
		if( data < cur->data )
			cur = cur->left;
		else if ( data > cur->data )
			cur = cur->right;
		else
		     break;
	}

	if ( cur == NULL )
		return NULL;
	else
		return cur;
}

int BTree::getCount() {
	return count;
}

int BTree::getLevel(Node* n) {
	int levelNo=0;
	Node *cur;
	Data d = n->data;

	if ( n == NULL )	/* if no node is present return level as -1 */
		return NO_NODE;

	cur = root;
	while ( cur != NULL ) {
		if ( d < cur->data ) {
			cur = cur->left;
			levelNo++;
		}
		else if ( d > cur->data ) {
			cur = cur->right;
			levelNo++;
		}
		else if ( d == cur->data )
			return levelNo;
	}
}

void BTree::printNode(Node* n) {
	if( n != NULL )
		cout<<n->data<<" ";
}

void BTree::printSubTree(Node* n) {

	if( n != NULL ) {
		printNode(n);
		printNode(n->left);
		printNode(n->right);
	}
}

void BTree::printTree(Node* n){

	if( n != NULL ) {
		printSubTree(n);
		printTree(n->left);
		printTree(n->right);
	}
}

int BTree::isRootNode(Node* n){
	return n == root ;
}

void BTree::_printlevel(Node* n,int levelNo) {
	static int tempLevel;


	if ( ( n != NULL) && (tempLevel++ < levelNo) )
	{
		tempLevel=0;

		if( getLevel(n) == levelNo)
			printNode(n);
		
		_printlevel(n->left,levelNo);
		tempLevel=0;
		_printlevel(n->right,levelNo);
		
	}
}

void BTree::printLevel(int levelNo) {
	_printlevel(root,levelNo);
}


Node* BTree::getRoot(){
	return root;
}

int main()
{
	BTree tree;
	Node *n=NULL;
	void printTree(Node*);


	tree.addNode(4.5);
	tree.addNode(-3.33);
	tree.addNode(12.6);
	tree.addNode(44.33);
	tree.addNode(12.56);
	tree.addNode(1.0);

	cout<<"Pre Order:"; tree.preOrder(); cout<<endl;
	cout<<"Post Order:"; tree.postOrder(); cout<<endl;
	cout<<"In Order:"; tree.inOrder(); cout<<endl;

	
	n = tree.getRoot();
	tree.printLevel(2);

	return 1;
}