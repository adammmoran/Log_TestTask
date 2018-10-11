package yrk.com.ua.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import yrk.com.ua.dao.CaseDAO;
import yrk.com.ua.dao.OrderlineDAO;
import yrk.com.ua.dao.ProductDAO;
import yrk.com.ua.models.Case;
import yrk.com.ua.models.Orderline;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RController {
    @Autowired
    ProductDAO productDAO;

    @Autowired
    CaseDAO caseDAO;

    @Autowired
    OrderlineDAO orderlineDAO;

    @GetMapping("/")
    public List<Case> main(){

        return Optimize((List<Case>)caseDAO.findAll(),(List<Orderline>)orderlineDAO.findAll());
    }

    private List<Case> Optimize(List<Case> cases, List<Orderline> orderlines){
        double[] V_ord = new double[orderlines.size()];
        for(int i=0; i < orderlines.size(); i++){
            V_ord[i] = orderlines.get(i).getProduct().getSizeX() *
                    orderlines.get(i).getProduct().getSizeY() *
                    orderlines.get(i).getProduct().getSizeZ();
            V_ord[i] = V_ord[i] * orderlines.get(i).getNumberOfProducts();
        }

        double[] V_cs = new double[cases.size()];
        for(int i=0;i<cases.size();i++){
            V_cs[i] = cases.get(i).getSizeX() *
                    cases.get(i).getSizeY() *
                    cases.get(i).getSizeZ();
        }

        double[][] Z = new double[orderlines.size()][cases.size()];
        double[] m = new double[orderlines.size()];
        int[] l = new int[orderlines.size()];
        for(int i=0;i<orderlines.size();i++){
            for(int j=0;j<cases.size();j++){
                double k = (V_ord[i]/V_cs[j])*100;
                if(k>0 && k <=100){
                    Z[i][j] = k;
                }
                else{
                    Z[i][j] = -1;
                }
            }
        }

        for(int i=0;i<orderlines.size();i++){
            double max = Z[i][0];
            for(int j=0;j<cases.size();j++){

                if(Z[i][j] > max){
                    max = Z[i][j];
                    l[i] = j;
                }
            }
            m[i] = max;
        }
        List<Case> BestCasesForOrders = new ArrayList<>();
        for(int i=0;i<orderlines.size();i++){
            BestCasesForOrders.add(cases.get(l[i]));
        }
        String S="";
        for(int i=0;i<orderlines.size();i++){
            S = S + "Order " + (i+1) + " : " +
                    BestCasesForOrders.get(i) + " : " +
                    orderlines.get(i).getProduct() + " : " +
                    "NumberOfProducts = " + orderlines.get(i).getNumberOfProducts()+"\n";
        }
        System.out.println(S);
        return BestCasesForOrders;
    }
}
