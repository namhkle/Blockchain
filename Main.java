/**
 * Write a description of class Main here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n*************************************");
        System.out.println("Welcome to the Blockchain");

        // creates the blockchain
        List<Block> blockChain = new ArrayList<>();

        // creates the genesis block to initialize the blockchain
        String[] genesisTransactions = {"Satoshi pays 17000000 BTC to Satoshi"};
        Block genesisBlock = new Block(0, genesisTransactions);

        blockChain.add(genesisBlock);

        int previousBlock = 0;    // keeps track of previousBlock to add to the blockchain
        boolean exit = false;
        while (!exit) {
            try {
                System.out.println("\nPlease select an option: \n1. Add a block. \n2. Edit a block. \n3. Display a block \n4. Display the whole Blockchain. \n5. Exit the Blockchain");
                Scanner sc = new Scanner(System.in);
                int option = sc.nextInt();
                switch (option) {
                    case 1:
                        List<String> takeTranasction = new ArrayList<>();
                        System.out.println("\nOption 1 selected. Adding a block.");
                        boolean done = false;

                        while (!done) {
                            System.out.println("Please enter a transaction:");

                            sc = new Scanner(System.in);
                            String transaction = sc.nextLine();

                            System.out.println("\nTransaction Accepted.");
                            System.out.println("1. Enter another transaction. \n0. Done\n");
                            sc = new Scanner(System.in);
                            option = sc.nextInt();

                            takeTranasction.add(transaction);

                            if (option == 0) {
                                // converts ArrayList to String array
                                String takeBlock[] = takeTranasction.toArray(new String[takeTranasction.size()]);

                                // adds the new block to the blockchain
                                Block block = new Block(blockChain.get(previousBlock).getBlockHash(), takeBlock);

                                blockChain.add(block);
                                done = true;
                                previousBlock++;   // after a new block is added, previousBlock increases by 1 to prepare for the next block addition
                            }
                            if (option == 1) {
                                System.out.println("Please enter another transaction:");
                                sc = new Scanner(System.in);
                                transaction = sc.nextLine();
                                takeTranasction.add(transaction);

                                System.out.println("\nTransaction Accepted.");
                                System.out.println("1. Enter another transaction. \n0. Done\n");
                                sc = new Scanner(System.in);
                                option = sc.nextInt();
                                if (option == 0) {
                                    // converts ArrayList to String array
                                    String takeBlock[] = takeTranasction.toArray(new String[takeTranasction.size()]);

                                    // adds the new block to the blockchain
                                    Block block = new Block(blockChain.get(previousBlock).getBlockHash(), takeBlock);
                                    previousBlock++;
                                    blockChain.add(block);
                                    done = true;
                                }
                            }

                        }
                        break;
                    case 2:
                        System.out.println("\nOption 2 selected. Editing a block.");
                        done = false;
                        while (!done) {
                            System.out.println("Please enter a block number: \n0. Done");
                            sc = new Scanner(System.in);
                            option = sc.nextInt();

                            // check whether the block exists
                            if (option > blockChain.size() || option < 0) {
                                System.out.println("\nSorry, the block does not exist\n");
                                continue;
                            } else if (option == 0) {
                                done = true;
                            } else {
                                int previousBlock2 = 0;
                                List<String> takeTranasction2 = new ArrayList<>();
                                while (!done) {
                                    System.out.println("Editing Block " + option + ".");
                                    System.out.println("Please enter a new transaction:");
                                    sc = new Scanner(System.in);
                                    String transaction = sc.nextLine();
                                    takeTranasction2.add(transaction);

                                    System.out.println("\nTransaction Accepted.");
                                    System.out.println("1. Enter another new transaction. \n0. Done\n");
                                    sc = new Scanner(System.in);
                                    int subOption = sc.nextInt();
                                    if (subOption == 0) {
                                        // converts ArrayList to String array
                                        String takeBlock[] = takeTranasction2.toArray(new String[takeTranasction2.size()]);

                                        // updates the previous block
                                        Block block = new Block(blockChain.get(option - 2).getBlockHash(), takeBlock);

                                        blockChain.set(option - 1, block);

                                        // updates all the blocks after
                                        for (int i = option - 1; i < blockChain.size(); i++) {
                                            Block updates = new Block(blockChain.get(i).getBlockHash(), blockChain.get(i + 1).getTransaction());
                                            blockChain.set(i + 1, updates);
                                        }
                                        done = true;
                                    }
                                }
                            }
                        }
                        break;
                    case 3:
                        done = false;
                        while (!done) {
                            System.out.println("\nOption 3 selected. Enter the block number to display: \n0. Done");
                            try {
                                sc = new Scanner(System.in);
                                option = sc.nextInt();

                                // check whether the block exists
                                if (option > blockChain.size() || option < 0) {
                                    System.out.println("Sorry, the block does not exist");
                                    continue;
                                } else if (option == 0) {
                                    done = true;
                                } else {
                                    System.out.println("Displaying the block " + option + ":");
                                    System.out.println("Block " + option + "\n    Previous Hash: " + blockChain.get(option - 1).getPreviousHash()
                                            + "\n    Current Hash :" + blockChain.get(option - 1).getBlockHash()
                                            + "\n    Transactions :" + Arrays.toString(blockChain.get(option - 1).getTransaction()) + "\n");

                                }
                            } catch (Exception e) {
                                System.out.println("\nInvalid option.");
                            }
                        }
                        break;
                    case 4:
                        System.out.println("\nOption 4 selected. Displaying the Blockchain.");
                        int blockNum = 1;
                        for (Block b : blockChain) {
                            System.out.println("Block " + blockNum + "\n    Previous Hash: " + b.getPreviousHash() + "\n    Current Hash :" + b.getBlockHash()
                                    + "\n    Transactions :" + Arrays.toString(b.getTransaction()) + "\n");
                            blockNum++;
                        }
                        System.out.println();
                        break;
                    case 5:
                        exit = true;
                        System.out.println("Blockchain exited. Bye bye.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("\nInvalid option.");
            }
        }

        /*System.out.println("*************************************");
        // creates the genesis block
        String[] genesisTransactions = {"Satoshi pays 1300000 BTC to Paul","Alice pays 5 BTC to Bob"};

        Block genesisBlock = new Block(0,genesisTransactions);

        System.out.println("The hash of the Genesis Block: " + "\n" + genesisBlock.getBlockHash());

        // creates the  2nd block and points to the genesis block
        String[] block2Transactions = {"Satoshi pays 210000 BTC to Paul","Satoshi pays 10000 BTC to Disney Land"};
        Block block2 = new Block(genesisBlock.getBlockHash(), block2Transactions);

        System.out.println("The hash of the Block 2: " + "\n" + block2.getBlockHash());

        // creates the 3rd block and points to the genesis block
        String[] block3Transactions = {"Satoshi pays 35000 BTC to Vuong","Satoshi pays 33333 BTC to Unicef"};
        Block block3 = new Block(block2.getBlockHash(), block3Transactions);

        System.out.println("The hash of the Block 3: " + "\n" + block3.getBlockHash());*/
    }
}
