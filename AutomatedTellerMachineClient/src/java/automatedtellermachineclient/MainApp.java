/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package automatedtellermachineclient;

import ejb.session.stateless.AtmCardSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.DepositAccountSessionBeanRemote;
import entity.AtmCard;
import entity.DepositAccount;
import exception.AtmCardNotFoundException;
import java.text.NumberFormat;
import java.util.Scanner;

/**
 *
 * @author jayso
 */
public class MainApp {

    private CustomerSessionBeanRemote customerSessionBean;
    private AtmCardSessionBeanRemote atmCardSessionBean;
    private DepositAccountSessionBeanRemote depositAccountSessionBean;

    public MainApp() {

    }

    public MainApp(CustomerSessionBeanRemote customerSessionBean, AtmCardSessionBeanRemote atmCardSessionBean, DepositAccountSessionBeanRemote depositAccountSessionBean) {
        this.customerSessionBean = customerSessionBean;
        this.atmCardSessionBean = atmCardSessionBean;
        this.depositAccountSessionBean = depositAccountSessionBean;
    }

    public void runApp() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("*** Welcome to Retail Core Banking System :: ATM Machine ***\n");
            System.out.println("1: Insert ATM Card");
            System.out.println("2: Exit\n");
            System.out.print("> ");
            int input = sc.nextInt();
            sc.nextLine();

            switch (input) {
                case 1:
                    doInsertATMCard(sc);
                    break;
                case 2:
                    System.out.println("You have exited. Goodbye.");
                    break;
                default:
                    System.out.println("Invalid input, please try again");
                    runApp();
                    break;
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage() + "\n");
            runApp();
        }
    }

    private void doInsertATMCard(Scanner sc) throws AtmCardNotFoundException {

        while (true) {
            System.out.println("=== Please Insert your ATM card ===\n");
            System.out.print("Enter ATM card number> ");
            String cardNum = sc.nextLine().trim();
            System.out.print("Enter PIN> ");
            String pin = sc.nextLine().trim();
            try {
                AtmCard card = atmCardSessionBean.retrieveAtmCardByNumber(cardNum); // Assuming is always enabled
                goToDashboard(sc, card);
                break;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "\n");
                System.out.print("Do you want to retry? (yes/no)> ");
                String retryChoice = sc.next().trim().toLowerCase();
                if (!retryChoice.equals("yes")) {
                    runApp();
                    break;
                }
            }
        }

    }

    private void goToDashboard(Scanner sc, AtmCard card) {
        System.out.println("=== Welcome back to RCBS :: ATM Machine ===\n");
        System.out.println("1: Change PIN");
        System.out.println("2: Enquire Available Balance");
        System.out.println("3: Exit\n");
        System.out.print("> ");
        int input = sc.nextInt();
        sc.nextLine();

        switch (input) {
            case 1:
                doChangePin(sc, card);
                break;
            case 2:
                doCheckBalance(sc, card);
                break;
            case 3:
                System.out.println("You have exited. Goodbye.");
                break;
            default:
                System.out.println("Invalid input, please try again");
                goToDashboard(sc, card);
                break;
        }
    }

    private void doChangePin(Scanner sc, AtmCard card) {
        while (true) {
            System.out.println("=== Retail Core Banking System :: Change PIN ===\n");
            System.out.print("Enter old PIN> ");
            String oldPin = sc.next().trim();
            System.out.print("Enter new PIN> ");
            String newPin = sc.next().trim();
            try {
                atmCardSessionBean.changePin(oldPin, newPin, card.getAtmCardId());
                System.out.println("Pin Changed Successfully");
                goToDashboard(sc, card);
                break;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "\n");
                System.out.print("Do you want to retry? (yes/no)> ");
                String retryChoice = sc.next().trim().toLowerCase();
                if (!retryChoice.equals("yes")) {
                    doChangePin(sc, card);
                    break;
                }
            }
        }
    }

    private void doCheckBalance(Scanner sc, AtmCard card) {
        while (true) {
            System.out.println("=== Retail Core Banking System :: Enquire Available Balance ===\n");
            System.out.print("Enter deposit account number> ");
            String acctNum = sc.next().trim();
            try {
                DepositAccount account = atmCardSessionBean.enquireAvailableBalance(card.getAtmCardId(), acctNum);
                System.out.println("Available Balance is " + NumberFormat.getCurrencyInstance().format(account.getAvailableBalance()));
                goToDashboard(sc, card);
                break;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "\n");
                System.out.print("Do you want to retry? (yes/no)> ");
                String retryChoice = sc.next().trim().toLowerCase();
                if (!retryChoice.equals("yes")) {
                    doCheckBalance(sc, card);
                    break;
                }
            }
        }
    }
}
