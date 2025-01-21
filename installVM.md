<div align="justify" style="margin-right:25px;margin-left:25px">

# How to install and configure the Azure virtual machine <!-- omit in toc -->

This document explains how to install and configure the Azure virtual machine.

## Table of contents <!-- omit in toc -->

- [Create an Azure account](#create-an-azure-account)
- [Create the virtual machine](#create-the-virtual-machine)

# Create an Azure account

To create an Azure account, go to the following link: [Azure](https://azure.microsoft.com/en-us/)

As a student, you can get a free account with 100$ of credit.

# Create the virtual machine

1. Go to the Azure portal: [Azure portal](https://portal.azure.com/)
2. Click on the "Virtual machines" button

![Virtual machines](imgs/azures_services.png)

3. Click on the "Create" then on "Azure virtual machine"

![Create virtual machine](imgs/create_vm.png)

4. Selecte a resource group, we create a new one "heig-vd-dai-course" or use an existing one

![Resource group](imgs/resource_group.png)

5. Fill in the virtual machine details, give it a name and change the availability zone to "Zone 3"

![Virtual machine details](imgs/instance_detail_1.png)

6. Now, select the image, we choose "Ubuntu Server 22.04 LTS" and the size "Standard B1s"

![Virtual machine details](imgs/instance_detail_2.png)

7. For the administrator account, we choose "SSH public key" and add our own public key (not shown here)

![Admin account](imgs/administrator_account.png)

8. For the networking, we choose "Allow selected ports" and selected "HTTP", "HTTPS" and "SSH"

![Inbound port rules](imgs/inbound_port.png)

9. Finally, click on "Review + create" and then on "Create"

The virtual machine is now created.

</div>