/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tellerterminalclient;

import ejb.session.stateless.AtmCardSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.DepositAccountSessionBeanRemote;
import entity.AtmCard;
import entity.Customer;
import entity.DepositAccount;
import enumeration.DepositAccountType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
        Scanner sc = new Scanner(System.in);
        System.out.println("*** Welcome to Retail Core Banking System :: Teller Terminal ***\n");
        System.out.println("1: Login");
        System.out.println("2: Exit\n");
        System.out.print("> ");
        int input = sc.nextInt();
        sc.nextLine();

        switch (input) {
            case 1:
                goToMenu(sc);
                break;
            case 2:
                System.out.println("You have exited. Goodbye.");
                break;
            default:
                System.out.println("Invalid input, please try again");
                runApp();
                break;
        }

    }

    private void goToMenu(Scanner sc) {
        System.out.println("=== Retail Core Banking System :: Teller Terminal Menu ===\n");
        System.out.println("1: New Customer");
        System.out.println("2: Existing Customer");
        System.out.println("3: Exit\n");
        System.out.print("> ");
        int input = sc.nextInt();
        sc.nextLine();

        switch (input) {
            case 1:
                doRegistration(sc);
                break;
            case 2:
                doLogin(sc);
                break;
            case 3:
                System.out.println("You have exited. Goodbye.");
                break;
            default:
                System.out.println("Invalid input, please try again");
                goToMenu(sc);
                break;
        }
    }

    private void doLogin(Scanner sc) {
        // As id and contact number is unique, it is sufficient to verify a customer
        while (true) {
            System.out.println("=== Retail Core Banking System :: Teller Terminal Login ===\n");
            System.out.print("Enter contact number> ");
            String contact = sc.nextLine().trim();
            System.out.print("Enter identification number> "); // Serves as password
            String id = sc.nextLine().trim();

            try {
                Customer customer = customerSessionBean.checkCredentials(id, contact);
                goToDashboard(sc, customer);
                break;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "\n");
                System.out.print("Do you want to retry? (yes/no)> ");
                String retryChoice = sc.nextLine().trim().toLowerCase();
                if (!retryChoice.equals("yes")) {
                    goToMenu(sc);
                    break;
                }
            }
        }
    }

    private void doRegistration(Scanner sc) {
        Customer customer = new Customer();

        while (true) {
            System.out.println("=== Retail Core Banking System :: Registration ===\n");
            System.out.print("Enter first name> ");
            customer.setFirstName(sc.nextLine().trim());
            System.out.print("Enter last name> ");
            customer.setLastName(sc.nextLine().trim());
            System.out.print("Enter identification number> ");
            customer.setIdentificationNumber(sc.nextLine().trim());
            System.out.print("Enter contact number> ");
            customer.setContactNumber(sc.nextLine().trim());
            System.out.print("Enter address line 1> ");
            customer.setAddressLine1(sc.nextLine().trim());
            System.out.print("Enter address line 2> ");
            customer.setAddressLine2(sc.nextLine().trim());
            System.out.print("Enter postal code> ");
            customer.setPostalCode(sc.nextLine().trim());

            try {
                customer = customerSessionBean.createNewCustomer(customer);
                System.out.println("New customer " + customer.getIdentificationNumber() + " created successfully!\n");
                goToDashboard(sc, customer);
                break;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "\n");
                System.out.print("Do you want to retry? (yes/no)> ");
                String retryChoice = sc.nextLine().trim().toLowerCase();
                if (!retryChoice.equals("yes")) {
                    goToMenu(sc);
                    break;
                }
            }
        }
    }

    private void goToDashboard(Scanner sc, Customer customer) {
        System.out.println("*** Welcome to RCBS Teller Terminal, " + customer.getFirstName() + " ***\n");
        System.out.println("1: Open Deposit Account");
        System.out.println("2: Issue ATM Card");
        System.out.println("3: Logout\n");
        System.out.print("> ");
        int input = sc.nextInt();
        sc.nextLine();

        switch (input) {
            case 1:
                doOpenAccount(sc, customer);
                break;
            case 2:
                doIssueCard(sc, customer);
                break;
            case 3:
                runApp();
                break;
            default:
                System.out.println("Invalid input, please try again");
                goToDashboard(sc, customer);
                break;
        }
    }

    private void doOpenAccount(Scanner sc, Customer customer) {
        while (true) {
            System.out.println("=== Retail Core Banking System :: Open Deposit Account ===\n");
            System.out.print("New account number> ");
            String accountNumber = sc.next().trim();
            System.out.print("Enter initial cash deposit amount> ");
            BigDecimal nominal = sc.nextBigDecimal();
            try {
                DepositAccount depositAccount = new DepositAccount(accountNumber, DepositAccountType.SAVINGS, nominal, nominal, true);
                depositAccount = depositAccountSessionBean.createNewAccount(depositAccount, customer.getCustomerId());
                System.out.println("New Deposit Account with account number " + depositAccount.getAccountNumber() + " is created successfully!\n");
                goToDashboard(sc, customer);
                break;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "\n");
                System.out.print("Do you want to retry? (yes/no)> ");
                String retryChoice = sc.next().trim().toLowerCase();
                if (!retryChoice.equals("yes")) {
                    goToDashboard(sc, customer);
                    break;
                }
            }
        }
    }

    private void doIssueCard(Scanner sc, Customer customer) {
        while (true) {
            System.out.println("=== Retail Core Banking System :: Issue ATM Card ===\n");
            try {
                if (customer.getCard() != null) {
                    System.out.print("You cannot own more than 1 atm card, would you like to replace the old one? (yes/no)> ");
                    String reply = sc.next().trim().toLowerCase();
                    if (reply.equals("yes")) {
                        atmCardSessionBean.deleteOldAtmCard(customer.getCard().getAtmCardId());
                        System.out.println("Old Atm Card successfully deleted\n");
                    } else {
                        goToDashboard(sc, customer);
                        break;
                    }
                }

                // New card
                System.out.print("Enter new card number> ");
                String cardNum = sc.next().trim();
                System.out.print("Enter name on card> ");
                String name = sc.next().trim();
                System.out.print("Enter pin number> ");
                String pin = sc.next().trim();
                AtmCard card = new AtmCard(cardNum, name, true, pin);

                // Linking deposit accounts to card
                List<Long> accounts = new ArrayList<>();
                System.out.println("Finding existing deposit accounts to link to card");
                for (DepositAccount account : customer.getDepositAccounts()) {
                    System.out.print("Do you want to link " + account.getAccountNumber() + "? (yes/no)> ");
                    String reply = sc.next().trim().toLowerCase();
                    if (reply.equals("yes")) {
                        accounts.add(account.getDepositAccountId());
                    }
                }

                if (!accounts.isEmpty()) {
                    card = atmCardSessionBean.createNewAtmCard(card, customer.getCustomerId(), accounts);
                    System.out.println("Credit Card created\n");
                    goToDashboard(sc, customer);
                    break;
                } else {
                    System.out.println("An ATM card must be associated with one or more deposit accounts\n");
                }

            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "\n");
                System.out.print("Do you want to retry? (yes/no)> ");
                String retryChoice = sc.next().trim().toLowerCase();
                if (!retryChoice.equals("yes")) {
                    doIssueCard(sc, customer);
                    break;
                }
            }
        }
    }

}
