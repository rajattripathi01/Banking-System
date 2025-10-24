# SmartBank (Console-Based Banking System)

SmartBank is a simple **console-based banking system** implemented in Java.  
It allows users to **create bank accounts, log in using a security PIN, deposit money, withdraw money, and record transaction history**, all stored in `.txt` files (no database).

The project is designed to demonstrate:
- User input validation
- File-based data persistence
- Basic object-oriented banking logic

---

## 🚀 Features

| Feature | Description |
|--------|-------------|
| Create New Account | User can register with name, account number, PIN, and first deposit (min ₹1000). |
| Login System | User login validated with account number + security PIN. |
| Deposit Money | Minimum deposit required: ₹500. Transaction stored in file. |
| Withdraw Money | Withdrawal only allowed if the balance is sufficient. Stored in file. |
| Transaction Logging | Every deposit/withdraw is logged with timestamp and transaction ID. |
| File Storage | User accounts stored in `src/Accounts/accounts.txt` and transactions in `src/Transactions/transactions.txt`. |


---

## 💾 Stored Data Format

**Account Example (accounts.txt):**


**Transaction Example (transactions.txt):**


---

## ⚠️ Limitations

- PIN stored as plain text — no encryption
- Full transaction-history option in UI (yet)
- File I/O only — no real database

---

## ✅ Future Improvements

| Enhancement | Benefit |
|------------|---------|
| Switch to SQL database | Reliability & querying |
| Encrypt PIN values | Security |
| Add "View Balance" & "View History" options | Better functionality |
| GUI (JavaFX or Swing) | Better user experience |

---

## Author
**Rajat Tripathi**

SmartBank was developed as a learning and practice project focusing on core Java, file handling, and object-oriented programming concepts.

