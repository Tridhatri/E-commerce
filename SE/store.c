/* C program for E-commerce CLI Based Grocery Store */
/* Last Modified : 05/01/2023 */
/* Last Modified : 12/01/2023 */
/* Author : Tridhatri Sontena */

/* No.of modifications : 5*/


// Firstly, let's include the header file

#include "store.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>



struct item it[100];
struct orders od[100];
int lastNonZeroIndex = -1;
int n;
int bufferSize = 255; //to read the items from the file 
int cartItemCount = 0;
#define MAX_CART_ITEMS 100



int getCurrentlyAvailableItems() {
    n = sizeof(it) / sizeof(it[0]);
    for (int j = n - 1; j >= 0; j--) {
        if (it[j].HeadQuantity != 0) {
            lastNonZeroIndex = j;
            break;
        }
    }
    return lastNonZeroIndex;
}
 

void displayItems()
{
for(int i=0; i<=lastNonZeroIndex; i++)
    {
    printf("Item Name : %s\n",it[i].ItemName);
    printf("Description : %s\n",it[i].Description);
    printf("Head Quantity : %d\n",it[i].HeadQuantity);
    printf("Base Price : %.2f\n",it[i].BasePrice);
}
};


// void writeItemstoFile(const char* filename)
// {
    
//     FILE *f = fopen(filename,"a");
//     if(f == NULL)
//     {
//         printf("Error opening file %s\n",filename);
//         exit(1);
//     }
//     else 
//     {
//         for(int i=0; i<=lastNonZeroIndex; i++){
//         fprintf(f,"%d,%s,%s,%d,%.2f\n",it[i].ItemID,it[i].ItemName,it[i].Description,it[i].HeadQuantity,it[i].BasePrice);
//     }
//     }
//     fclose(f);
// }

// void writeItemstoFile(const char* filename) {
//     FILE* f = fopen(filename, "w");
//     if (f == NULL) {
//         printf("Error opening file %s\n", filename);
//         exit(1);
//     } else {
//         for (int i = 0; i <= lastNonZeroIndex; i++) {
//             fprintf(f, "%d,%s,%s,%d,%.2f\n", it[i].ItemID, it[i].ItemName, it[i].Description, it[i].HeadQuantity, it[i].BasePrice);
//         }
//         fclose(f);
//     }
// }

// void writeItemstoFile(const char* filename) {
//     FILE* f = fopen(filename, "w");
//     if (f == NULL) {
//         printf("Error opening file %s\n", filename);
//         exit(1);
//     } else {
//         for (int i = 0; i <= lastNonZeroIndex; i++) {
//             fprintf(f, "%d,%s,%s,%d,%.2f\n", it[i].ItemID, it[i].ItemName, it[i].Description, it[i].HeadQuantity, it[i].BasePrice);
//         }
//         fclose(f);
//     }
// }

void writeItemstoFile(const char* filename) {
    FILE* f = fopen(filename, "a");
    if (f == NULL) {
        printf("Error opening file %s\n", filename);
        exit(1);
    } else {
        for (int i = 0; i <= lastNonZeroIndex; i++) {
            fprintf(f, "%d,%s,%s,%d,%.2f\n", it[i].ItemID, it[i].ItemName, it[i].Description, it[i].HeadQuantity, it[i].BasePrice);
        }
        fclose(f);
    }
}




void readItemsfromFile(const char* filename)
{
    FILE *f = fopen(filename,"r");
    if(f == NULL){
        
        printf("Error opening file %s\n",filename);
        exit(1);
    }
    else 
    {

        char buffer[bufferSize];
        printf("The items in the grocery store are as follows:\n");
        while(fgets(buffer,bufferSize,f)){
            
            printf("%s",buffer);
        }
    }
}

// void loaditemsfromFile(const char* filename)
// {
//     FILE *f = fopen(filename,"r");
//     if(f == NULL){
//         printf("Error opening file %s\n",filename);
//         exit(1);
//     }
//     else 
//     {
//         char buffer[bufferSize];
//         while(fgets(buffer,bufferSize,f)){
//             char *token = strtok(buffer,",");
//             while(token != NULL){
//                 for(int i = 0; i < 4; i++){
//                   // it[i].ItemID = token;
//                 token = strtok(NULL,",");
//                 }
//             }
            
//         }
//     }
// }

void addToCart(const char *itemName, int quantity) {
    printf("Present value of cartItemcount = %d\n", cartItemCount);
    printf("max cart items count = %d\n", MAX_CART_ITEMS);
    if (cartItemCount < MAX_CART_ITEMS) {
        // Find the item in the store
        printf("value of getCurrentAvailableItems() = %d\n", getCurrentlyAvailableItems());
        for (int i = 0; i <= getCurrentlyAvailableItems(); i++) {
            printf("Comparing: '%s' vs. '%s'\n", itemName, it[i].ItemName);
            if (strcasecmp(itemName, it[i].ItemName) == 0) {
                // Add the item to the cart
                strcpy(od[cartItemCount].ItemName, it[i].ItemName);
                strcpy(od[cartItemCount].Description, it[i].Description);
                od[cartItemCount].Quantity = quantity;
                od[cartItemCount].TotalPrice = quantity * it[i].BasePrice;

                cartItemCount++;
                printf("Item added to the cart.\n");
                return;
            }
        }

        printf("Item not found in the inventory.\n");
    } else {
        printf("Cart is full. Cannot add more items.\n");
    }
}



void displayCart() {
    printf("Shopping Cart:\n");
    for (int i = 0; i < cartItemCount; i++) {
        printf("Item Name : %s\n", od[i].ItemName);
        printf("Description : %s\n", od[i].Description);
        printf("Quantity : %d\n", od[i].Quantity);
        printf("Total Price : %.2f\n", od[i].TotalPrice);
        printf("\n");
    }
}

float checkout() {
    float total = 0.0;
    for (int i = 0; i < cartItemCount; i++) {
        total += od[i].TotalPrice;
    }
    cartItemCount = 0; // Reset the cart after checkout
    return total;
}

void editCart(){

}

int loadItemsFromFile(const char* filename) {
    FILE* f = fopen(filename, "r");
    if (f == NULL) {
        printf("Error opening file %s\n", filename);
        exit(1);
    } else {
        int i = 0;
        int readCount;
        char line[256];

        // Skip the header line
        if (fgets(line, sizeof(line), f) == NULL) {
            printf("Error reading header from %s\n", filename);
            exit(1);
        }

        // Read each line and parse the items
        while (fgets(line, sizeof(line), f) != NULL) {
            if (line[0] == '\n') {
                continue; // Skip empty lines
            }

            readCount = sscanf(line, "%d,%49[^,],%99[^,],%d,%f",
                               &it[i].ItemID, it[i].ItemName, it[i].Description, &it[i].HeadQuantity, &it[i].BasePrice);

            if (readCount == 5) {
                i++;
            } else {
                printf("Error parsing line %d in %s\n", i + 1, filename);
                exit(1);
            }
        }

        lastNonZeroIndex = i - 1;
        fclose(f);

        // Print the loaded items for verification
        printf("Loaded items from %s:\n", filename);
        for (int j = 0; j <= lastNonZeroIndex; j++) {
            printf("ItemID: %d, ItemName: %s, Description: %s, HeadQuantity: %d, BasePrice: %.2f\n",
                   it[j].ItemID, it[j].ItemName, it[j].Description, it[j].HeadQuantity, it[j].BasePrice);
        }

        return lastNonZeroIndex;
    }
}






