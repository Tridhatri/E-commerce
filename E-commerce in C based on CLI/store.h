#ifndef STORE_H
#define STORE_H

#define MAX_ITEMS 100 
#define MAX_CART_ITEMS 100

extern int lastNonZeroIndex;
extern int n;
struct item
{
    int ItemID;
    char ItemName[50];
    char Description[100];
    int HeadQuantity;
    float BasePrice;
};


extern struct item it[MAX_ITEMS];

extern int cartItemCount;
struct orders 
{
int ItemID;
char ItemName[50];
char Description[100];
int Quantity;
float TotalPrice;
};
extern struct orders od[MAX_CART_ITEMS];



void displayItems();
int getCurrentlyAvailableItems();
void writeItemstoFile(const char* filename);
void readItemsfromFile(const char* filename);

int loadItemsFromFile(const char* filename); //It loads the items read from the file into the array/memory 

void addToCart(const char *itemName, int quantity);
//It adds the selected item and the quantity to the cart from the availabile items
void displayCart();
float checkout();

void editCart();


#endif
