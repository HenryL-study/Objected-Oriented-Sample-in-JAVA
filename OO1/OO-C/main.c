#include <stdio.h>
#include <stdlib.h>

////////////////////////////////////
//Test examples
//1. {(12,23),(2,1),(5,4)} + {(12,3),(0,1),(5,4)}
//2. {(12,23),(002,1),(5,4)}
//3. {(12,23),(0,1),(5,4)}
//4. {(12,23),(000002,1),(5,4)}
//5. {(1,1)}
//6. {(1,1)} + {(0,0)}
//   {(1,1)} + {(0sd,0)}
///////////////////////////////////
struct mono     //����ʽ�ṹ
{
    int coeff;  //ϵ��
    int index;  //��
}poly[1000000],ans[1000000];

//����ȽϺ���
int cmp(const void *a , const void *b)
{
    struct mono *c = (struct mono *)a;
    struct mono *d = (struct mono *)b;

    return c->index - d->index;
}

//  ��������ַ�����������100W

int input();  //���뺯��

int main()
{
    int input_num, total_num = 0, i,k;  //total_numΪ������
    int isfind = 0, symbol = 0, skip = 0;  //symbol Ϊ�����
    char a = ' ';
    printf("Please input your polynomials, the space will be automatically ignored.\n");
    input_num = input();
    while(input_num > 0)
    {
        //�����жϷ���
        while(a == ' ')
        {
            a = getchar();
        }
        if(a == '+')
        {
            symbol = 0;
        }
        else if(a == '-')
        {
            symbol = 1;
        }
        else if(a == '\n')
        {
            symbol = 0;
            skip = 1;
        }
        else
        {
            symbol = -1;
            printf("Wrong input.");
            return 0;
        }
        //��ʼ�Ӽ�
        for(i=0;i<input_num;i++)
        {
            isfind = 0;
            for(k=0;k<total_num;k++)
            {
                if(ans[k].index == poly[i].index)
                {
                    if(symbol == 0)
                        ans[k].coeff += poly[i].coeff;
                    else
                        ans[k].coeff -= poly[i].coeff;
                    isfind = 1;
                    break;
                }
            }
            if(isfind == 0)
            {
                ans[total_num] = poly[i];
                if(symbol == 1)
                    ans[total_num].coeff = 0 - ans[total_num].coeff;
                total_num++;
            }
        }
        if(skip == 1)
            break;
        input_num = input();
        if(input_num == -1)
            return 0;
    }
    for(i=0;i<total_num;i++)
    {
        printf("%d, %d\n", ans[i].coeff, ans[i].index);
    }
    return 0;
}

int input()
{
    int notwrong = 1;
    int coe = 0,idx = 0, i = 0, num, p;
    char a = ' ';
    while(a == ' ')
    {
        a = getchar();
    }
    if(a == '{')    //�������ʽ
    {
        //inpoly = 1;
        do
        {
            a = getchar();
            while(a == ' ')
            {
                a = getchar();
            };
            if(a == '(')   //��������ɨ��
            {
                notwrong = scanf("%d", &coe);
                if(!notwrong && coe < 0)
                {
                    printf("Wrong input.");
                    return -1;
                }
                a = getchar();
                while(a == ' ')
                {
                    a = getchar();
                };
                if(a != ',')
                {
                    printf("Wrong input.");
                    return -1;
                }
                notwrong = scanf("%d", &idx);
                if(!notwrong && idx < 0)
                {
                    printf("Wrong input.");
                    return -1;
                }
                a = getchar();
                while(a == ' ')
                {
                    a = getchar();
                };
                if(a != ')')
                {
                    printf("Wrong input.");
                    return -1;
                }
                a = getchar();
                while(a == ' ')
                {
                    a = getchar();
                };
            }
            else
            {
                printf("Wrong input.");
                return -1;
            }
            poly[i].coeff = coe;
            poly[i].index = idx;
            i++;
        }while(a == ',');
        if(a != '}')
        {
            printf("Wrong input.");
            return -1;
        }
    }
    else if(a == '\n')
    {
        return 0;
    }
    else
    {
        printf("Wrong input.");
        return -1;
    }
    // ���򣬺ϲ�ͬ����
    qsort(poly,i,sizeof(poly[0]),cmp);
    for(num=0;num<i;num++)
    {
        if(poly[num+1].index == poly[num].index)
        {
            poly[num].coeff += poly[num+1].coeff;
            for(p=num+1;p<i-1;p++)
            {
                poly[p] = poly[p+1];
            }
            i--;
            poly[i].coeff = poly[i].index = 0; //ɾ�����һ��
        }
    }
    return i;
}
