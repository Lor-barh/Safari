package com.example.safariwebstore008.services.servicesImpl;

import com.example.safariwebstore008.dto.FundWalletRequest;
import com.example.safariwebstore008.dto.WithdrawalDto;
import com.example.safariwebstore008.enums.TransactionType;
import com.example.safariwebstore008.exceptions.InsufficientFundsException;
import com.example.safariwebstore008.models.User;
import com.example.safariwebstore008.models.Wallet;
import com.example.safariwebstore008.models.WalletTransaction;
import com.example.safariwebstore008.repositories.UserRepository;
import com.example.safariwebstore008.repositories.WalletRepository;
import com.example.safariwebstore008.repositories.WalletTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class WalletServiceImplTest {
 @Mock
    private WalletRepository walletRepository;
 @Mock
    private WalletTransactionRepository walletTransactionRepository;
 @Mock
    private UserRepository userRepository;
 @InjectMocks
    private WalletServiceImpl walletService;

 @Test
 void fundWalletSuccessfully() throws InsufficientFundsException {
   LocalDateTime localDateTime = LocalDateTime.now();
           Date date = Date.valueOf(LocalDate.now());
     Optional<Wallet> wallet = Optional.of(new Wallet());
     wallet.get().setWalletBalance(0.00);
     wallet.get().setCreateDate(localDateTime);


     FundWalletRequest fundWalletRequest= new FundWalletRequest();
     fundWalletRequest.setEmail("adababy@gmail.com");
     fundWalletRequest.setAmount(5000.00);
     fundWalletRequest.setTransactionDate(date);


     Optional<User> user = Optional.of(new User());
     user.get().setEmail("adababy@gmail.com");
     user.get().setId(1L);

     wallet.get().setUser(user.get());

     WalletTransaction walletTransaction= new WalletTransaction();
     walletTransaction.setWallet(wallet.get());
     walletTransaction.setAmount(fundWalletRequest.getAmount());
     walletTransaction.setTransactionDate(date);
     // walletTransaction.setUserModel(user.get());
     walletTransaction.setTransactionType(TransactionType.FUNDWALLET);


     Mockito.when(userRepository.findUserByEmail(fundWalletRequest.getEmail())).thenReturn(user);
     Mockito.when(walletRepository.save(wallet.get())).thenReturn(wallet.get());
     Mockito.when(walletRepository.findWalletByUserEmail(fundWalletRequest.getEmail())).thenReturn(wallet);
     Mockito.when(walletTransactionRepository.save(walletTransaction)).thenReturn(walletTransaction);
     Wallet wallet1= walletService.topUpWalletAccount(fundWalletRequest);



     Assertions.assertThat(wallet1.getWalletBalance()).isNotNull();
     Assertions.assertThat(wallet1.getWalletBalance()).isEqualTo(fundWalletRequest.getAmount());
    }

    @Test
    void checkWalletBalance() {
        String email = "inno@gmail.com";
        Wallet wallet = new Wallet();
        wallet.setId(1L);
        wallet.setWalletBalance(5000D);

        when(walletRepository.findWalletByUserEmail(email)).thenReturn(Optional.of(wallet));
        Double walletBalance = walletService.checkWalletBalance(email);
        System.out.println(walletBalance);
        assertEquals(walletBalance, wallet.getWalletBalance());
    }

    @Test
    void withdrawFromWallet() throws InsufficientFundsException {
        String email = "og@gmail.com";
        WithdrawalDto withdrawalDto = new WithdrawalDto();
        withdrawalDto.setAmount(2000.0);
        Wallet wallet = new Wallet();
        wallet.setWalletBalance(3000.0);
        when(walletRepository.findWalletByUserEmail(email)).thenReturn(Optional.of(wallet));
        Wallet wallet1 = walletService.withdrawFromWallet(withdrawalDto, email);
        assertThat(wallet1.getWalletBalance()).isEqualTo(1000.0);
    }
}