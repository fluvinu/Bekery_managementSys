import java.util.Scanner;

public class MainApp {
    private static Scanner sc= new Scanner(System.in);
    static AdminService admin;
    static CostermerService cus =null;
    static String role=null;
    public static void main(String[] args) {
        if(admin==null && cus==null){
            System.out.println("enter exit in uname and pass to exit");
            System.out.println("enter username");
            String uname = sc.next();
            System.out.println("enter pass");
            String uPass= sc.next();
            User u =new User(uname,uPass);
            role= Login.checkRol(u);
            System.out.println(role);
        }
        try {
            switch (role) {
                case "admin":
                    admin = new AdminImp();
                    adminDisplay();
                    break;
                case "user":
                    cus = new ConstmerImp();
                    cusDisplay();
                    break;
                case "exit":
                    System.out.println("good bye");
                    System.exit(0);
                default:
                    System.out.println("invalid opration");

            }
        }catch (NullPointerException e){
            System.out.println(e);
            System.out.println("ummm... this is hppn bcz may be the user and pas was wrong");
        }
        main(args);
    }
    static void adminDisplay(){
        int n=0;
        System.out.println("select oftion");
        System.out.println("1 add product");
        System.out.println("2 updaete product");
        System.out.println("3 delete product");
        System.out.println("4 diplay all prodct");
        System.out.println("5 sigh out");
        int ch= sc.nextInt();
        switch (ch){
            case 1:
                System.out.println("enter product name ");
                String pName = sc.next();
                System.out.println("enter pPrice ");
                double pPrice= sc.nextDouble();
                System.out.println("ente pqty");
                int pQty= sc.nextInt();
                Product p = new Product(pName,pPrice,pQty);
                 n= admin.appProduct(p);
                System.out.println(n);
                break;
            case 2:
                System.out.println("0 enter 0 (zero) to back");
                System.out.println("ente id to update product ");
                int pId= sc.nextInt();
                if(pId==0){
                    break;
                }
                System.out.println("enter upfated name ");
                String name= sc.next();
                System.out.println("enter update product qty ");
                int qty= sc.nextInt();
                System.out.println("enter ptoduct updated price ");
                double uPrice= sc.nextDouble();
                Product pro=new Product(pId,name,uPrice,qty);
                 n= admin.updateProduct(pro);
                System.out.println("update "+n);
                break;
            case 3:
                System.out.println("0 enter 0 (zero) to back");
                System.out.println("enter product id ");
                int id = sc.nextInt();
                if(id==0){
                    break;
                }
                n=admin.deleteProduct(id);
                System.out.println(n +"row updated");
                break;
            case 4:
                 for (Product pror:admin.vieProduct(role)){
                     System.out.println(pror.getPid()+"  "+pror.getpQty()+"  "+pror.getUserNmae()+"  "+pror.getUserPas());
                 }
                 break;
            case 5:
                String[] args = new String[0];
                admin=null;
                MainApp.main(args);
        }
    }

    static void cusDisplay(){
        System.out.println("select oftion");
        System.out.println("1 view product");
        System.out.println("2 Place order");
        System.out.println("3 Cancel order perticular item");
        System.out.println("4 cencel whole order ");
        System.out.println("5 Search product by id");
        System.out.println("6 Display all orders");
        System.out.println("7 sigh out");
        int ch = sc.nextInt();
        switch (ch){
            case 1:
                System.out.println("==============================================");
                for(Product pro: cus.vieProduct(role)){
                    System.out.println(pro.getPid()+" "+pro.getUserNmae()+" "+pro.getUserPas());
                }
                System.out.println("==============================================");
                break;
            case 2:
                System.out.println("==============================================");
                for(Product pro: cus.vieProduct(role)){
                    System.out.println(pro.getPid()+" "+pro.getUserNmae()+" "+pro.getUserPas());
                }
                System.out.println("==============================================");
                System.out.println("0 enter 0 (zero) to back");
                System.out.println("enter product id ");
                int pid = sc.nextInt();
                if(pid==0){
                    break;
                }
                System.out.println("enter quntity");
                int qty= sc.nextInt();
                boolean validOk=cus.valoOk(pid,qty);
                if(validOk){
                    int n= cus.placeOrder(role,pid,qty);
                    System.out.println(n+"row updated");
                }else {
                    System.out.println("qutyty is more then ur limit");
                }
                break;
            case 3:
                System.out.println("==============================================");
                for (Orders uOr:cus.viewOrders(role)){
                    System.out.println(uOr.getOid()+" "+uOr.getPid()+" "+uOr.getName()+" "+uOr.getQty()+" "+uOr.getCost()+" "+uOr.getUserName());
                }
                System.out.println("==============================================");
                System.out.println("enter name of product");
                String name = sc.next();
                System.out.println("enter oid");
                int oid = sc.nextInt();
                int n=cus.cencelOrderByName(name,oid,role);
                System.out.println(n+" row deleted");
                break;
            case 4:
                System.out.println("==============================================");
                for (Orders uOr:cus.viewOrders(role)){
                    System.out.println(uOr.getOid()+" "+uOr.getPid()+" "+uOr.getName()+" "+uOr.getQty()+" "+uOr.getCost()+" "+uOr.getUserName());
                }
                System.out.println("==============================================");
                System.out.println("enter order id ");
                int oidDeletAll = sc.nextInt();
                n=cus.deleteWholeOrder(oidDeletAll,role);

                break;
            case 5:
                System.out.println("==============================================");
                System.out.println("enter Product Id ");
                int pidS = sc.nextInt();
                System.out.println("still working on that....");
                for(Product pr:cus.vieProductById(pidS)){
                    System.out.println(pr.getPid()+"  "+pr.getUserNmae()+"  "+pr.getUserPas());
                }
                System.out.println("==============================================");
                break;
            case 6:
                System.out.println("==============================================");
                double cost=0;
                for (Orders uOr:cus.viewOrders(role)){
                    System.out.println(uOr.getOid()+" "+uOr.getPid()+" "+uOr.getName()+" "+uOr.getQty()+" "+uOr.getCost()+" "+uOr.getUserName());
                    cost=cost+uOr.getCost();
                }
                System.out.println("total cost is = "+cost);
                System.out.println("==============================================");
                break;
            case 7:
                System.out.println("==============================================");
                String[] args = new String[0];
                cus=null;
                MainApp.main(args);

        }
    }

}
