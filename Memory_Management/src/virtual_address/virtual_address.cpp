/**
 * This file contains implementations for methods in the VirtualAddress class.
 *
 * You'll need to add code here to make the corresponding tests pass.
 */

#include "virtual_address/virtual_address.h"

using namespace std;

VirtualAddress VirtualAddress::from_string(int process_id, string address) {
    string p = address.substr(0,PAGE_BITS);
    string o = address.substr(PAGE_BITS, ADDRESS_BITS);
    return VirtualAddress(process_id, bitset<32>(p).to_ulong(), bitset<32>(o).to_ulong());
}


string VirtualAddress::to_string() const {
    return std::bitset<PAGE_BITS>(page).to_string() + std::bitset<OFFSET_BITS>(offset).to_string();
}


ostream& operator <<(ostream& out, const VirtualAddress& address) {
    out << "PID " << std::to_string(address.process_id) << " @ "
    << std::bitset<address.PAGE_BITS>(address.page).to_string() 
    << std::bitset<address.OFFSET_BITS>(address.offset).to_string()
    << " [page: " << std::to_string(address.page) << "; offset: " << std::to_string(address.offset) << "]";
    return out;
}
