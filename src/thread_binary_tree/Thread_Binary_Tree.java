/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread_binary_tree;

import java.util.Scanner;
import thread_binary_tree.Node.bool;

/**
 *
 * @author Usman Ghani Mughal
 */
class Node
{
    public enum bool{thread,link}
    Node left_ptr;
    Node right_ptr;
    bool left;
    bool right;
    int info;
    Node()
    {
     this.left_ptr=null;
     this.right_ptr=null;
    }
    Node(int d)
    {
        this.info=d;
        this.right_ptr=null;
        this.left_ptr=null;
    }
}
class parloc
{
    Node parent;
    Node location;
}
class NodeImpl
{
    
  protected  int infinty;
  protected  Node Head;
  NodeImpl()
  {
     this.infinty=9999;
  }
  public void insertHead()
  {
      this.Head=new Node();
      this.Head.info=this.infinty;
      this.Head.left=bool.thread;  
      this.Head.right=bool.link;
      this.Head.left_ptr=this.Head;
      this.Head.right_ptr=this.Head;
  }
  protected parloc find(int item,parloc obj)
  {
      Node ptr,ptrsave;
      if(this.Head.left_ptr==this.Head)
      {
          obj.location=null;
          obj.parent=this.Head;
          return obj;
      }
      if(item==this.Head.left_ptr.info)
      {
          obj.location=this.Head.left_ptr;
          obj.parent=this.Head;
          return obj;
      }
      ptr=this.Head.left_ptr;
       ptrsave=this.Head;
      while(ptr!=this.Head)
      {
          ptrsave=ptr;
          if(item<ptr.info)
          {
              if(ptr.left==bool.link)
              {
                  ptr=ptr.left_ptr;
              }
              else
              {
                  break;
              }
          }
          else
          {
              if(item>ptr.info)
              {
                  if(ptr.right==bool.link)
                  {
                      ptr=ptr.right_ptr;
                  }
                  else
                  {
                      break;
                  }
              }
          }
          if(item==ptr.info)
          {
              obj.location=ptr;
              obj.parent=ptrsave;
              return obj;
          }
      }
      obj.location=null;
      obj.parent=ptrsave;
      return obj;
  }
  public void insert(int item)
  {
      Node tmp;
      parloc obj=new parloc();
      obj=find(item,obj);
      if(obj.location!=null)
      {
          System.out.println("Item is already In Tree");
          return;
      }
      tmp=new Node(item);
      tmp.left=bool.thread;
      tmp.right=bool.thread;
      if(obj.parent==this.Head)
      {
          System.out.println("**Data is Stored in The Root Node**");
          this.Head.left=bool.link;
          this.Head.left_ptr=tmp;
          
          tmp.left_ptr=this.Head;
          tmp.right_ptr=this.Head;
      }
      else
      {
          if(item<obj.parent.info)
          {
              System.out.println("***Data is Stored in left child of : "+obj.parent.info+"***\n");
              tmp.left_ptr=obj.parent.left_ptr;
              tmp.right_ptr=obj.parent;
              obj.parent.left=bool.link;
              obj.parent.left_ptr=tmp;
          }
          else
          {
              System.out.println("***Data is Stored in Right child of : "+obj.parent.info+"***\n");
              tmp.left_ptr=obj.parent;
              tmp.right_ptr=obj.parent.right_ptr;
              obj.parent.right=bool.link;
              obj.parent.right_ptr=tmp;
          }
      }
  }
  public void del(int item)
  {
    parloc obj=new parloc();
    if(this.Head==null)
    {
        System.out.println("Tree Is Empty");
        return;
    }
    obj=find(item,obj);
    if(obj.location==null)
    {
        System.out.println("Iteam is not Present in Tree");
        return;
    }
    if(obj.location.left==bool.thread&&obj.location.right==bool.thread)
    {
        case_a(obj);
    }
    if(obj.location.left==bool.link&&obj.location.right==bool.thread)
    {
        case_b(obj);
    }
    if(obj.location.left==bool.thread&&obj.location.right==bool.link)
    {
        case_b(obj);
    }
    if(obj.location.left==bool.link&&obj.location.right==bool.link)
    {
        case_c(obj);
    }
  }
  protected void case_a(parloc obj)
  {
      if(obj.parent==this.Head)
      {
          this.Head.left=bool.thread;
          this.Head.left_ptr=this.Head;
      }
      else
      {
          if(obj.location==obj.parent.left_ptr)
          {
              obj.parent.left=bool.thread;
              obj.parent.left_ptr=obj.location.left_ptr;
          }
          else
          {
              obj.parent.right=bool.thread;
              obj.parent.right_ptr=obj.location.right_ptr;
          }
      }
  }
  protected void case_b(parloc obj)
  {
      Node child,s,p;
      if(obj.location.left==bool.link)
      {
          child=obj.location.left_ptr;
      }
      else
      {
          child=obj.location.right_ptr;
      }
      if(obj.parent==this.Head)
      {
          this.Head.left_ptr=child;
      }
      else
      {
          if(obj.location==obj.parent.left_ptr)
          {
              obj.parent.left_ptr=child;
          }
          else
          {
              obj.parent.right_ptr=child;
          }
      }
      s=in_succ(obj.location);
      p=in_pred(obj.location);
      if(obj.location.right==bool.link)
      {
          s.left_ptr=p;
      }
      else
      {
          if(obj.location.left==bool.link)
          {
              p.right_ptr=s;
          }
      }
  }
  protected Node in_succ(Node loc)
  {
      Node succ;
      if(loc.right==bool.thread)
      {
          succ=loc.right_ptr;
      }
      else
      {
          loc=loc.right_ptr;
          while(loc.left==bool.link)
          {
              loc=loc.left_ptr;
          }
          succ=loc;
      }
      return succ;
  }
  protected Node in_pred(Node loc)
  {
      Node pred;
      if(loc.left==bool.thread)
      {
          pred=loc.left_ptr;
      }
      else
      {
          loc=loc.left_ptr;
          while(loc.right==bool.link)
          {
              loc=loc.right_ptr;
          }
          pred=loc;
      }
      return pred;
  }
  protected void case_c(parloc obj)
  {
      Node ptr,ptrsave,suc,parsuc,s,p;
      ptrsave=obj.location;
      ptr=obj.location.right_ptr;
      while(ptr.left==bool.link)
      {
          ptrsave=ptr;
          ptr=ptr.left_ptr;
      }
      suc=ptr;
      parsuc=ptrsave;
      obj.location.info=suc.info;
      if(suc.left==bool.thread&&suc.right==bool.thread)
      {
          case_a(obj);
      }
      else
      {
          case_b(obj);
      }
  }
  public void inorder()
  {
      Node ptr;
      if(this.Head.left_ptr==this.Head)
      {
          System.out.println("Tree is Empty");
          return;
      }
      ptr=this.Head.left_ptr;
      while(ptr.left==bool.link)
      {
          ptr=ptr.left_ptr;
      }
      System.out.print(ptr.info+" ");
      while(true)
      {
          ptr=in_succ(ptr);
          if(ptr==this.Head)
          {
              break;
          }
          System.out.print(ptr.info+" ");
      }
  }
}



public class Thread_Binary_Tree {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int choice,val;
        Scanner sc=new Scanner(System.in);
        NodeImpl ob=new NodeImpl();
        ob.insertHead();
        while(true)
        {
           System.out.println("\npress 1 for insert");
           System.out.println("press 2 for delete");
           System.out.println("press 3 for inorder");
           choice=sc.nextInt();
        switch(choice)
           {
             case 1:
             {
                System.out.print("Enter a number : ");
                val=sc.nextInt();
                ob.insert(val);
                break;
             }
             case 2:
             {
                System.out.print("Enter a number : ");
                val=sc.nextInt();
                ob.del(val);
                break;
             }
             case 3:
             {
                 System.out.println("Ans of Inorder Travers\n");
               ob.inorder();
               break;
             }
             default:
             {
                System.out.print("You Gave wrong Input");
             }
           }  
        }
    }
    
}
