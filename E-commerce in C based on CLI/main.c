#include <stdio.h>
#include <stdlib.h>
#include "store.h"
#define MAX_ITEMS 100 
  void readItemsfromArray(struct item it[], int lastNonZeroIndex) {
    printf("Items in the array:\n");
    for (int i = 0; i <= lastNonZeroIndex; i++) {
        printf("ItemID: %d, ItemName: %s, Description: %s, HeadQuantity: %d, BasePrice: %.2f\n",
               it[i].ItemID, it[i].ItemName, it[i].Description, it[i].HeadQuantity, it[i].BasePrice);
    }
} 
int main (){
printf("Welcome to the Grocery Store\n");
//Menu driven CLI based
 
    int option;
    // switch variable for the function to be executed
    int user;
    //switch variable for Identifying buyer or shopkeeper
    int no_of_items_to_insert;
    int select;
    int cartItemCount = 0;
    //Selection variable for conversing through the store
    //You have to store the existing items in the file in the array of items it
  
   
        // Add this line at the beginning of the main function in main.c
  loadItemsFromFile("items.csv"); 
    
        
         n = sizeof(it) / sizeof(it[0]);
    printf("Enter 1 for Shopkeeper or 2 for Buyer\n");
    scanf("%d", &user);
    //lastNonZeroIndex = loadItemsFromFile("items.csv");
    switch (user)
    {
    //Case 1 : Shopkeeper
    case 1:
            printf("Hello Shopkeeper\n");
            int action;
            printf("Enter 1 to insert items\n");
            printf("Enter 2 to view current items\n");
            printf("Enter 3 to delete items\n");
            scanf("%d", &action);
            switch(action)
            {
                 case 1:
                       //printf("%f",it[1].BasePrice);
                        printf("Enter how many items to insert\n");
                        scanf("%d", &no_of_items_to_insert);
                        lastNonZeroIndex = getCurrentlyAvailableItems();
                        //lastNonZeroIndex = loadItemsFromFile("items.csv");

                        printf("%d\n", lastNonZeroIndex);
                        
                        printf("n value is %d\n", n);

                        if(no_of_items_to_insert > 0 && no_of_items_to_insert<= n-lastNonZeroIndex)
                        {
                            printf("inside if block");
                            for (int i = lastNonZeroIndex + 1; i <= no_of_items_to_insert+lastNonZeroIndex; i++)
                            {
                                    // Allow the user to enter item details
                                    it[i].ItemID = lastNonZeroIndex+1; 
                                    printf("Enter details for Item %d:\n", i + 1);
                                    printf("Item Name: ");
                                    scanf("%49s", it[i].ItemName);
                                    printf("Description: ");
                                    scanf("%99s", it[i].Description);
                                    printf("Head Quantity: ");
                                    scanf("%d", &it[i].HeadQuantity);
                                    printf("Base Price: ");
                                    scanf("%f", &it[i].BasePrice);
                            }
                            printf("\n");
                        // Update lastNonZeroIndex after inserting items
                        lastNonZeroIndex += no_of_items_to_insert;
                        //displayItems();
                        writeItemstoFile("items.csv");
                            
                        }
                        else
                        {
                            printf("Only %d slots avaliable in this store",n);
                        }
                        break;
              
            
            

                // case 1:
                //        //printf("%f",it[1].BasePrice);
                //         printf("Enter how many items to insert\n");
                //         scanf("%d", &no_of_items_to_insert);
                //         lastNonZeroIndex = getCurrentlyAvailableItems();
                //         //lastNonZeroIndex = loadItemsFromFile("items.csv");

                //         printf("%d\n", lastNonZeroIndex);
                //         if(no_of_items_to_insert > 0 && no_of_items_to_insert<= n-lastNonZeroIndex)
                //         {
                //             for (int i = lastNonZeroIndex + 1; i < no_of_items_to_insert; i++)
                //             {
                //                     // Allow the user to enter item details
                //                     it[i].ItemID = lastNonZeroIndex+1; 
                //                     printf("Enter details for Item %d:\n", i + 1);
                //                     printf("Item Name: ");
                //                     scanf("%49s", it[i].ItemName);
                //                     printf("Description: ");
                //                     scanf("%99s", it[i].Description);
                //                     printf("Head Quantity: ");
                //                     scanf("%d", &it[i].HeadQuantity);
                //                     printf("Base Price: ");
                //                     scanf("%f", &it[i].BasePrice);
                //             }
                //             printf("\n");
                //         // Update lastNonZeroIndex after inserting items
                //         lastNonZeroIndex += no_of_items_to_insert;
                //         //displayItems();
                //         writeItemstoFile("items.csv");
                            
                //         }
                //         else
                //         {
                //             printf("Only %d slots avaliable in this store",n);
                //         }
                //         break;
    //             case 1:
    // printf("Enter how many items to insert\n");
    // scanf("%d", &no_of_items_to_insert);

    // // Ensure there is space in the array to insert items
    // if (lastNonZeroIndex + no_of_items_to_insert < MAX_ITEMS) {
    //     for (int i = 0; i < no_of_items_to_insert; i++) {
    //         // Allow the user to enter item details
    //         it[lastNonZeroIndex + i].ItemID = lastNonZeroIndex + i + 1;
    //         printf("Enter details for Item %d:\n", lastNonZeroIndex + i + 1);
    //         printf("Item Name: ");
    //         scanf("%49s", it[lastNonZeroIndex + i].ItemName);
    //         printf("Description: ");
    //         scanf("%99s", it[lastNonZeroIndex + i].Description);
    //         printf("Head Quantity: ");
    //         scanf("%d", &it[lastNonZeroIndex + i].HeadQuantity);
    //         printf("Base Price: ");
    //         scanf("%f", &it[lastNonZeroIndex + i].BasePrice);
    //     }
    //     // Update lastNonZeroIndex after inserting items
    //     lastNonZeroIndex += no_of_items_to_insert;
    //     writeItemstoFile("items.csv");
    // } else {
    //     printf("Not enough space to insert items.\n");
    // }
    // break;

                case 2:
                        readItemsfromFile("items.csv");
            }
            
            break;
    //Case 2 : Buyer
    case 2:
        printf("Hello Buyer\n");
        while(1)
        {
        printf("\n\nOptions\n");
        printf("Option 1 : Show Items List\n");
        printf("\n");
        printf("Option 2 : Add Items to Cart\n");
        printf("\n");
        printf("Option 3 : Show Items in Cart\n");
        printf("\n");
        printf("Option 4 : Edit Items in Cart\n");
        printf("\n");
        printf("Enter your desired option: ");
        printf("\n");

        scanf("%d", &option);
        

        // switch statement
        switch (option) 
        {
        case 1:
            //readItemsfromFile("items.csv");
            readItemsfromArray(it, lastNonZeroIndex);
            // printf("Press q to quit");
            // scanf("%d",&select);

            break;
 
        case 2:
      
           // Add item to the cart
                 printf("Enter the item name: ");
                char itemName[50];
                scanf("%49s", itemName);

                printf("Enter the quantity: ");
                int quantity;
                scanf("%d", &quantity);

                addToCart(itemName, quantity);
                break;
            
 
        case 3:
             // View the shopping cart
                displayCart();
                 break;

        case 4: 
        //editing the items in the cart
            editCart();
 
        default:
            printf("");
            break;
        }
        }
        
        break;
    }


}