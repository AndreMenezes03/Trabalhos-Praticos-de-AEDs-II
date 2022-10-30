#include <stdbool.h>
#include <stdio.h>
#include <string.h>
#include <strings.h>

bool palindromo(char palina[1000], int i, int j)
{
    bool resp = true;
    if (i <= j)
    {
        if (palina[i] != palina[j])
        {
            resp = false;
        }
        else
        {
            resp = palindromo(palina, i + 1, j - 1);
        }
    }
    return resp;
}

int main(void)
{
    int i = 0;
    char palin[1000];
    while (palin != "FIM")
    {
        fgets(palin, 1000, stdin);
        if (strncmp(palin, "FIM",3) != 0)
        {
            int tamanho = (strlen(palin) - 1);
            if (palin[tamanho] == '\n')
                tamanho -= 1;
            bool result = palindromo(palin, i, tamanho);
            if (result == true)
            {
                printf("SIM\n");
            }
            else
            {
                printf("NAO\n");
            }
        }else{
            break;
        }
    }
    return 0;
}